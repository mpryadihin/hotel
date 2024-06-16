package unit.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import ru.mpryadihin.diplom.models.Personal;
import ru.mpryadihin.diplom.models.Schedule;
import ru.mpryadihin.diplom.repositories.PersonalRepository;
import ru.mpryadihin.diplom.repositories.ScheduleRepository;
import ru.mpryadihin.diplom.servicies.ScheduleService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ScheduleServiceTest {

    @Mock
    ScheduleRepository scheduleRepository;

    @Mock
    PersonalRepository personalRepository;

    @InjectMocks
    ScheduleService scheduleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        List<Schedule> schedules = Arrays.asList(new Schedule(), new Schedule());
        when(scheduleRepository.findAll()).thenReturn(schedules);

        List<Schedule> result = scheduleService.findAll();

        assertEquals(schedules, result);
        verify(scheduleRepository, times(1)).findAll();
    }

    @Test
    public void testFindOne(){
        Schedule schedule = new Schedule();
        schedule.setId(1);

        when(scheduleRepository.findById(schedule.getId())).thenReturn(Optional.of(schedule));

        Schedule result = scheduleService.findOne(schedule.getId());

        assertEquals(schedule,result);
        verify(scheduleRepository, times(1)).findById(schedule.getId());
    }

    @Test
    public void testSave(){
        Schedule schedule = new Schedule();

        scheduleService.save(schedule);

        verify(scheduleRepository, times(1)).save(schedule);
    }

    @Test
    public void testUpdate(){
        Schedule updatedSchedule = new Schedule();

        scheduleService.update(1, updatedSchedule);

        verify(scheduleRepository, times(1)).save(updatedSchedule);
    }

    @Test
    public void testDelete(){
        int id = 1;
        scheduleService.delete(id);

        verify(scheduleRepository, times(1)).deleteById(id);
    }

    @Test
    public void testUpdateAll(){
        Schedule schedule1 = new Schedule();
        Schedule schedule2 = new Schedule();
        schedule1.setId(1);
        schedule1.setId(2);
        schedule1.setMonth(3);
        schedule2.setMonth(3);
        schedule1.setYear(2024);
        schedule2.setYear(2024);
        schedule1.setDay1(4);
        schedule2.setDay2(8);

        List<Schedule> scheduleList = Arrays.asList(schedule1,schedule2);

        Schedule updatedSchedule1 = new Schedule();
        Schedule updatedSchedule2 = new Schedule();
        updatedSchedule1.setId(1);
        updatedSchedule2.setId(2);
        updatedSchedule1.setMonth(4);
        updatedSchedule2.setMonth(4);
        updatedSchedule1.setYear(2026);
        updatedSchedule2.setYear(2026);
        updatedSchedule1.setDay1(2);
        updatedSchedule2.setDay2(4);

        List<Schedule> updatedSchedules = Arrays.asList(updatedSchedule1, updatedSchedule2);

        when(scheduleRepository.findById(1)).thenReturn(Optional.of(schedule1));
        when(scheduleRepository.findById(2)).thenReturn(Optional.of(schedule2));

        scheduleService.updateAll(updatedSchedules);

        assertEquals(schedule1.getYear(),2026);
        assertEquals(schedule2.getDay2(), 4);
    }

    @Test
    public void testFindAllByYearAndMonth() {
        int year = 2024;
        int month = 5;
        Schedule schedule1 = new Schedule();
        Schedule schedule2 = new Schedule();
        schedule1.setId(1);
        schedule2.setId(2);
        schedule1.setYear(2024);
        schedule2.setYear(2024);
        schedule1.setMonth(5);
        schedule2.setMonth(5);
        List<Schedule> schedules = Arrays.asList(schedule1, schedule2);

        when(scheduleRepository.findAllByYearAndMonth(year, month)).thenReturn(schedules);
        List<Schedule> result = scheduleService.findAllByYearAndMonth(year, month);
        assertEquals(schedules.size(), result.size());
    }

    @Test
    public void testAllDays(){
        int year = 2024;
        int month = 4;
        List<Integer> result = scheduleService.allDays(year,month);
        List<Integer> values1to31 = IntStream.rangeClosed(1,31).boxed().toList();
        List<Integer> values1to30 = IntStream.rangeClosed(1,30).boxed().toList();
        List<Integer> values1to29 = IntStream.rangeClosed(1,29).boxed().toList();
        List<Integer> values1to28 = IntStream.rangeClosed(1,28).boxed().toList();

        assertEquals(scheduleService.allDays(year,month), values1to30);
        assertNotEquals(scheduleService.allDays(year, month), values1to28);
    }

    @Test
    public void testGetMonthValue() {
        int month = 5;
        assertEquals(month - 1, scheduleService.getMonthValue(month).getMonth());
    }
    @Test
    public void testGetSalary() {
        int year = 2024;
        int month = 5;
        int expectedSalary = 5;
        Schedule schedule = new Schedule();
        schedule.setDay1(1);
        Personal personal = new Personal();
        personal.setId(1);
        personal.setSalary(5);
        schedule.setPersonal(personal);
        List<Schedule> schedules = List.of(schedule);
        when(scheduleRepository.findAllByYearAndMonth(year,month)).thenReturn(schedules);
        assertEquals(expectedSalary, scheduleService.getSalary(year, month));
    }

}
