package ru.mpryadihin.diplom.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mpryadihin.diplom.models.Personal;
import ru.mpryadihin.diplom.models.Schedule;
import ru.mpryadihin.diplom.repositories.PersonalRepository;
import ru.mpryadihin.diplom.repositories.ScheduleRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    private final PersonalRepository personalRepository;

    @Autowired
    public ScheduleService(PersonalRepository personalRepository, ScheduleRepository scheduleRepository, PersonalRepository personalRepository1) {
        this.scheduleRepository = scheduleRepository;
        this.personalRepository = personalRepository1;
    }
    @Cacheable(value = "ScheduleService::findAll")
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }
    @Cacheable(value = "ScheduleService::findOne", key = "#id")
    public Schedule findOne(int id){
        Optional<Schedule> foundSchedules = scheduleRepository.findById(id);
        return foundSchedules.orElse(null);
    }
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "ScheduleService::findAll", allEntries = true),
            @CacheEvict(value = "ScheduleService::findOne", key = "#schedule.id")
    })
    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "ScheduleService::findAll", allEntries = true),
            @CacheEvict(value = "ScheduleService::findOne", key = "#id")
    })
    public void update(int id, Schedule updatedSchedule){
        updatedSchedule.setId(id);
        scheduleRepository.save(updatedSchedule);
    }
    @Transactional(readOnly = false)
    @CacheEvict(value = "ScheduleService::findAll")
    public void updateAll(List<Schedule> updatedSchedule){
        for (Schedule s : updatedSchedule){
            Optional<Schedule> existingScheduleOptional = scheduleRepository.findById(s.getId());
            if (existingScheduleOptional.isPresent()) {
                Schedule existingSchedule = existingScheduleOptional.get();
                existingSchedule.setIdPersonal(s.getIdPersonal());
                existingSchedule.setMonth(s.getMonth());
                existingSchedule.setYear(s.getYear());
                existingSchedule.setDay1(s.getDay1());
                existingSchedule.setDay2(s.getDay2());
                existingSchedule.setDay3(s.getDay3());
                existingSchedule.setDay4(s.getDay4());
                existingSchedule.setDay5(s.getDay5());
                existingSchedule.setDay6(s.getDay6());
                existingSchedule.setDay7(s.getDay7());
                existingSchedule.setDay8(s.getDay8());
                existingSchedule.setDay9(s.getDay9());
                existingSchedule.setDay10(s.getDay10());
                existingSchedule.setDay11(s.getDay11());
                existingSchedule.setDay12(s.getDay12());
                existingSchedule.setDay13(s.getDay13());
                existingSchedule.setDay14(s.getDay14());
                existingSchedule.setDay15(s.getDay15());
                existingSchedule.setDay16(s.getDay16());
                existingSchedule.setDay17(s.getDay17());
                existingSchedule.setDay18(s.getDay18());
                existingSchedule.setDay19(s.getDay19());
                existingSchedule.setDay20(s.getDay20());
                existingSchedule.setDay21(s.getDay21());
                existingSchedule.setDay22(s.getDay22());
                existingSchedule.setDay23(s.getDay23());
                existingSchedule.setDay24(s.getDay24());
                existingSchedule.setDay25(s.getDay25());
                existingSchedule.setDay26(s.getDay26());
                existingSchedule.setDay27(s.getDay27());
                existingSchedule.setDay28(s.getDay28());
                existingSchedule.setDay29(s.getDay29());
                existingSchedule.setDay30(s.getDay30());
                existingSchedule.setDay31(s.getDay31());
                scheduleRepository.save(existingSchedule);
            }
        }
    }
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "ScheduleService::findAll", allEntries = true),
            @CacheEvict(value = "ScheduleService::findOne", key = "#id")
    })
    public void delete(int id) {
        scheduleRepository.deleteById(id);
    }
    @Transactional(readOnly = false)
    public List<Schedule> findAllByYearAndMonth(int year, int month){
        init(year, month);
        return scheduleRepository.findAllByYearAndMonth(year, month);
    }
    public List<Integer> allDays(int year, int month){

        List<Integer> days = new ArrayList<>();

        switch (month) {
            case 2 -> {
                if (year % 4 == 0) {
                     days = IntStream.rangeClosed(1, 29)
                            .boxed()
                            .collect(Collectors.toList());
                } else {
                     days = IntStream.rangeClosed(1, 28)
                            .boxed()
                            .collect(Collectors.toList());
                }
            }
            case 4, 6, 9, 11 ->
                days = IntStream.rangeClosed(1, 30)
                    .boxed()
                    .collect(Collectors.toList());
            case 1, 3, 5, 7, 8, 10, 12 ->
                days = IntStream.rangeClosed(1, 31)
                    .boxed()
                    .collect(Collectors.toList());
        }
        return days;
    }
    public Date getMonthValue(int month){
        Date monthValue = new Date();
        monthValue.setMonth(month-1);
        return monthValue;
    }
    private void deleteAllScheduleByYear(int year){ //удаляем график работы за прошлый год
        List<Schedule> scheduleList = scheduleRepository.findAllByYear(year - 1);
        if (!scheduleList.isEmpty()) {
            scheduleRepository.deleteAllByYear(year - 1); //удаляем раз в год, для статистики
        }
    }
    private Schedule createSchedule(Personal personal, int year, int month){
        Schedule schedule = new Schedule();
        schedule.setIdPersonal(personal.getId());
        schedule.setYear(year);
        schedule.setMonth(month);
        schedule.setPersonal(personal);
        return schedule;
    }

    private void init(int year, int month){
        deleteAllScheduleByYear(year);
        List<Schedule> findData = scheduleRepository.findAllByYearAndMonth(year, month);
        List<Personal> allPersonal = personalRepository.findAll();
        for (Personal p : allPersonal){
            if (findData.stream().noneMatch(x -> x.getIdPersonal() == p.getId())){
                save(createSchedule(p, year, month));
            }
        }
    }
    public int getSalary(int year, int month){
        List<Schedule> schedules = scheduleRepository.findAllByYearAndMonth(year,month);
        if (schedules.isEmpty()) {
            return 0;
        } else {
            return schedules.stream().mapToInt(x -> (x.getDay1() + x.getDay2() + x.getDay3() + x.getDay4() + x.getDay5() + x.getDay6() + x.getDay7() +
                    x.getDay8() + x.getDay9() + x.getDay10() + x.getDay11() + x.getDay12() + x.getDay13() + x.getDay14() + x.getDay15() + x.getDay16() +
                    x.getDay17() + x.getDay18() + x.getDay19() + x.getDay20() + x.getDay21() + x.getDay22() + x.getDay23() + x.getDay24() + x.getDay25() +
                    x.getDay26() + x.getDay27() + x.getDay28() + x.getDay29() + x.getDay30() + x.getDay31()) * x.getPersonal().getSalary()).sum();
        }
    }
}
