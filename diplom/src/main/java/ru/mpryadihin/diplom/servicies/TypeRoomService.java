package ru.mpryadihin.diplom.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mpryadihin.diplom.models.TypeRoom;
import ru.mpryadihin.diplom.repositories.TypeRoomRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TypeRoomService {

    private final TypeRoomRepository typeRoomRepository;
    @Autowired
    public TypeRoomService(TypeRoomRepository typeRoomRepository) {
        this.typeRoomRepository = typeRoomRepository;
    }
    @Cacheable(value = "TypeRoomService::findAll")
    public List<TypeRoom> findAll(){
        return typeRoomRepository.findAll();
    }
    @Cacheable(value = "TypeRoomService::findOne", key = "#id")
    public TypeRoom findOne(int id){
        Optional<TypeRoom> foundedType = typeRoomRepository.findById(id);
        return foundedType.orElse(null);
    }
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "TypeRoomService::findAll", allEntries = true),
            @CacheEvict(value = "TypeRoomService::findOne", key = "#typeRoom.id")
    })
    public void save(TypeRoom typeRoom){
        typeRoomRepository.save(typeRoom);
    }
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "TypeRoomService::findAll", allEntries = true),
            @CacheEvict(value = "TypeRoomService::findOne", key = "#id")
    })
    public void update(int id, TypeRoom updatedType){
        updatedType.setId(id);
        typeRoomRepository.save(updatedType);
    }
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "TypeRoomService::findAll", allEntries = true),
            @CacheEvict(value = "TypeRoomService::findOne", key = "#id")
    })
    public void delete(int id){
        typeRoomRepository.deleteById(id);
    }
}
