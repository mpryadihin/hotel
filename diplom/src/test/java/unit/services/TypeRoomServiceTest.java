package unit.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mpryadihin.diplom.models.TypeRoom;
import ru.mpryadihin.diplom.repositories.TypeRoomRepository;
import ru.mpryadihin.diplom.servicies.TypeRoomService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TypeRoomServiceTest {

    @Mock
    private TypeRoomRepository typeRoomRepository;

    @InjectMocks
    private TypeRoomService typeRoomService;

    @Test
    void findAll_ReturnsListOfTypeRooms() {
        List<TypeRoom> typeRooms = new ArrayList<>();
        typeRooms.add(new TypeRoom());
        typeRooms.add(new TypeRoom());
        when(typeRoomRepository.findAll()).thenReturn(typeRooms);

        List<TypeRoom> result = typeRoomService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void findOne_WithValidId_ReturnsTypeRoom() {
        int id = 1;
        TypeRoom typeRoom = new TypeRoom();
        when(typeRoomRepository.findById(id)).thenReturn(Optional.of(typeRoom));

        TypeRoom result = typeRoomService.findOne(id);

        assertEquals(typeRoom, result);
    }

    @Test
    void findOne_WithInvalidId_ReturnsNull() {
        int id = 1;
        when(typeRoomRepository.findById(id)).thenReturn(Optional.empty());

        TypeRoom result = typeRoomService.findOne(id);

        assertNull(result);
    }

    @Test
    void save_CallsSaveMethodInRepository() {
        TypeRoom typeRoom = new TypeRoom();

        typeRoomService.save(typeRoom);

        verify(typeRoomRepository, times(1)).save(typeRoom);
    }

    @Test
    void update_CallsSaveMethodInRepositoryWithUpdatedTypeRoom() {
        int id = 1;
        TypeRoom updatedTypeRoom = new TypeRoom();
        updatedTypeRoom.setId(id);

        typeRoomService.update(id, updatedTypeRoom);

        verify(typeRoomRepository, times(1)).save(updatedTypeRoom);
    }

    @Test
    void delete_CallsDeleteByIdMethodInRepository() {
        int id = 1;

        typeRoomService.delete(id);

        verify(typeRoomRepository, times(1)).deleteById(id);
    }
}
