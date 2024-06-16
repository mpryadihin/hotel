package unit.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.mpryadihin.diplom.models.Resides;
import ru.mpryadihin.diplom.models.Room;
import ru.mpryadihin.diplom.repositories.ResidesRepository;
import ru.mpryadihin.diplom.repositories.RoomRepository;
import ru.mpryadihin.diplom.servicies.RoomService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ResidesRepository residesRepository;

    @InjectMocks
    private RoomService roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindAll() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room());
        rooms.add(new Room());

        when(roomRepository.findAll()).thenReturn(rooms);

        List<Room> result = roomService.findAll();

        assertEquals(2, result.size());
        verify(roomRepository, times(1)).findAll();
    }

    @Test
    void testFindOne() {
        int id = 1;
        Room room = new Room();
        room.setId(id);
        Optional<Room> optionalRoom = Optional.of(room);

        when(roomRepository.findById(id)).thenReturn(optionalRoom);

        Room result = roomService.findOne(id);

        assertEquals(id, result.getId());
        verify(roomRepository, times(1)).findById(id);
    }

    @Test
    void testSave() {
        Room room = new Room();
        room.setId(1);
        room.setIdTypeRoom(1);

        roomService.save(room);

        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void testUpdate() {
        int id = 1;
        Room updatedRoom = new Room();
        updatedRoom.setId(id);
        updatedRoom.setIdTypeRoom(1);
        roomService.update(id, updatedRoom);

        verify(roomRepository, times(1)).save(updatedRoom);
    }

    @Test
    void testDelete() {
        int id = 1;

        roomService.delete(id);

        verify(roomRepository, times(1)).deleteById(id);
    }

    @Test
    void testCountPrice() {
        Room room = new Room();
        room.setIdTypeRoom(1);
        room.setSeats(2);

        int result = roomService.countPrice(room);

        assertEquals(1800, result);
    }

    @Test
    void testFindFreeRooms() {

        List<Room> allRooms = new ArrayList<>();

        Room room = new Room();
        room.setId(1);
        allRooms.add(room);
        List<Resides> residesList = new ArrayList<>();
        Resides resides = new Resides();
        resides.setId(1);
        resides.setIdRoom(room.getId());
        resides.setDateIn(LocalDate.now());
        resides.setDateOut(LocalDate.now().plusDays(2));
        residesList.add(resides); // Sample resides

        when(roomRepository.findAll()).thenReturn(allRooms);
        when(residesRepository.findAll()).thenReturn(residesList);

        List<Room> freeRooms = roomService.findFreeRooms();


        assertEquals(0, freeRooms.size()); // Since the sample room is occupied, no free rooms should be returned
    }

    @Test
    void testFindDateResides() {

        Resides resides = new Resides();
        resides.setId(1);
        resides.setDateIn(LocalDate.now().minusDays(1));
        resides.setDateOut(LocalDate.now().plusDays(1));
        List<Resides> residesList = new ArrayList<>();
        residesList.add(resides);

        when(residesRepository.findByIdRoom(resides.getId())).thenReturn(residesList);

        List<Resides> result = roomService.findDateResides(resides.getId());


        assertEquals(1, result.size());
        assertEquals(resides.getIdRoom(), result.get(0).getIdRoom());
    }
}
