package unit.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mpryadihin.diplom.models.Resides;
import ru.mpryadihin.diplom.models.Room;
import ru.mpryadihin.diplom.repositories.ResidesRepository;
import ru.mpryadihin.diplom.servicies.ResidesService;
import ru.mpryadihin.diplom.servicies.RoomService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResidesServiceTest {

    @Mock
    private ResidesRepository residesRepository;

    @Mock
    private RoomService roomService;

    @InjectMocks
    private ResidesService residesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindAll() {
        Resides resides1 = new Resides();
        Resides resides2 = new Resides();
        List<Resides> residesList = Arrays.asList(resides1, resides2);

        when(residesRepository.findAll()).thenReturn(residesList);

        List<Resides> result = residesService.findAll();

        assertEquals(2, result.size());
        verify(residesRepository, times(1)).findAll();
    }

    @Test
    void testFindOne() {
        int id = 1;
        Resides resides = new Resides();
        when(residesRepository.findById(id)).thenReturn(Optional.of(resides));

        Resides result = residesService.findOne(id);

        assertNotNull(result);
        verify(residesRepository, times(1)).findById(id);
    }

    @Test
    void testSave() {

        Resides resides = new Resides();
        resides.setId(1);
        resides.setDateIn(LocalDate.of(2024,6,4));
        resides.setDateOut(LocalDate.of(2024,6,8));
        Room room = new Room();
        room.setId(1);
        resides.setRoom(room);
        when(roomService.findOne(anyInt())).thenReturn(room);


        residesService.save(resides);

        verify(residesRepository, times(1)).save(resides);
    }

    @Test
    void testUpdate() {
        int id = 1;
        Resides updatedResides = new Resides();
        updatedResides.setDateIn(LocalDate.of(2024,6,4));
        updatedResides.setDateOut(LocalDate.of(2024,6,8));
        Room room = new Room();
        room.setId(1);
        updatedResides.setRoom(room);
        when(roomService.findOne(anyInt())).thenReturn(room);

        residesService.update(id, updatedResides);

        verify(residesRepository, times(1)).save(updatedResides);
    }

    @Test
    void testDelete() {
        int id = 1;

        residesService.delete(id);

        verify(residesRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetResultPrice() {
        Resides resides = new Resides();
        resides.setId(1);
        Room room = new Room();
        room.setId(1);
        room.setPrice(3);
        resides.setRoom(room);
        resides.setDateIn(LocalDate.of(2024,6,4));
        resides.setDateIn(LocalDate.now().minusDays(2));
        resides.setDateOut(LocalDate.now());

        when(roomService.findOne(anyInt())).thenReturn(room);

        int result = residesService.getResultPrice(resides);

        assertEquals(6, result); //2*3
    }

    @Test
    void testDateValidation() {
        Resides inResides = new Resides();
        inResides.setId(1);
        inResides.setIdRoom(1);
        inResides.setDateIn(LocalDate.now().minusDays(3));
        inResides.setDateOut(LocalDate.now().minusDays(1));
        //----------------------------------------------//
        Resides nowResides = new Resides();
        nowResides.setId(2);
        nowResides.setIdRoom(1);
        nowResides.setDateIn(LocalDate.now().minusDays(3));
        nowResides.setDateOut(LocalDate.now().minusDays(2));
        when(residesRepository.findByIdRoom(1)).thenReturn(List.of(inResides));

        int result = residesService.dateValidation(nowResides);

        assertEquals(0, result);
    }

    @Test
    void testFindByYear() {
        Resides resides1 = new Resides();
        Resides resides2 = new Resides();
        resides1.setDateIn(LocalDate.of(2024, 1, 1));
        resides1.setDateOut(LocalDate.of(2024, 1, 10));
        resides2.setDateIn(LocalDate.of(2025, 1, 1));
        resides2.setDateOut(LocalDate.of(2025, 1, 10));
        List<Resides> residesList = Arrays.asList(resides1, resides2);
        when(residesRepository.findAll()).thenReturn(residesList);

        List<Resides> result = residesService.findByYear(2024);

        assertEquals(1, result.size()); // Expecting only resides1 in the result for year 2024
    }
}
