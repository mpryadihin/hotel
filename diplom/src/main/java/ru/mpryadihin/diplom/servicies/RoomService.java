package ru.mpryadihin.diplom.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mpryadihin.diplom.models.Resides;
import ru.mpryadihin.diplom.models.Room;
import ru.mpryadihin.diplom.repositories.ResidesRepository;
import ru.mpryadihin.diplom.repositories.RoomRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RoomService {
    private final RoomRepository roomRepository;
    private final ResidesRepository residesRepository;
    @Autowired
    public RoomService(RoomRepository roomRepository, ResidesRepository residesRepository) {
        this.roomRepository = roomRepository;
        this.residesRepository = residesRepository;
    }

    @Cacheable(value = "RoomService::findAll")
    public List<Room> findAll(){
        return roomRepository.findAll();
    }

    @Cacheable(value = "RoomService::findOne", key = "#id")
    public Room findOne(int id){
        Optional<Room> foundedRoom = roomRepository.findById(id);
        return foundedRoom.orElse(null);
    }


    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "RoomService::findAll", allEntries = true),
            @CacheEvict(value = "RoomService::findOne", key = "#room.id")
    })
    public void save(Room room){
        room.setPrice(countPrice(room));
        roomRepository.save(room);
    }
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "RoomService::findAll", allEntries = true),
            @CacheEvict(value = "RoomService::findOne", key = "#id")
    })
    public void update(int id, Room updatedRoom){
        updatedRoom.setPrice(countPrice(updatedRoom));
        updatedRoom.setId(id);
        roomRepository.save(updatedRoom);
    }
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "RoomService::findAll", allEntries = true),
            @CacheEvict(value = "RoomService::findOne", key = "#id")
    })
    public void delete(int id){
        roomRepository.deleteById(id);
    }

    public int countPrice(Room room){
      int temp = switch (room.getIdTypeRoom()) {
          case 1 -> 1000;
          case 2 -> 1200;
          case 3 -> 1500;
          case 4 -> 2000;
          case 5 -> 3500;
          case 6 -> 4200;
          case 7 -> 5000;
          default -> throw new IllegalStateException("Unexpected value: " + room.getIdTypeRoom());
      };
      return temp + (room.getSeats() * 400);
    }

    public List<Room> findFreeRooms(){
        List<Room> all = roomRepository.findAll();
        List<Resides> resides = residesRepository.findAll();
        LocalDate now = LocalDate.now();
        for (Resides res : resides){
            if ((now.equals(res.getDateIn()) || now.isAfter(res.getDateIn())) && now.isBefore(res.getDateOut()) || now.equals(res.getDateOut())) {
                all.removeIf(item -> item.getId() == res.getIdRoom());
            }
        }
        return all;
    }
    public List<Resides> findDateResides(int idRoom){
        List<Resides> resides = residesRepository.findByIdRoom(idRoom);
        LocalDate now = LocalDate.now();
        resides.removeIf(r -> r.getDateOut().isBefore(now));
        return resides;
    }


}
