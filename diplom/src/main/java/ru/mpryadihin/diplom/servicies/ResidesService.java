package ru.mpryadihin.diplom.servicies;

import org.modelmapper.ModelMapper;
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


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional(readOnly = true)
public class ResidesService {
    private final ResidesRepository residesRepository;
    private final RoomService roomService;
    @Autowired
    public ResidesService(ResidesRepository residesRepository, RoomService roomService, ModelMapper modelMapper) {
        this.residesRepository = residesRepository;
        this.roomService = roomService;
    }
    @Cacheable(value = "ResidesService::findAll")
    public List<Resides> findAll(){
        return residesRepository.findAll();
    }
    @Cacheable(value = "ResidesService::findOne", key = "#id")
    public Resides findOne(int id){
        Optional<Resides> foundResides = residesRepository.findById(id);
        return foundResides.orElse(null);
    }
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "ResidesService::findAll", allEntries = true),
            @CacheEvict(value = "ResidesService::findOne", key = "#resides.id")
    })
    public void save(Resides resides){
        resides.setResultPrice(getResultPrice(resides));
        residesRepository.save(resides);
    }
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "ResidesService::findAll", allEntries = true),
            @CacheEvict(value = "ResidesService::findOne", key = "#id")
    })
    public void update(int id, Resides updatedResides){
        updatedResides.setResultPrice(getResultPrice(updatedResides));
        updatedResides.setIdClient(id);
        residesRepository.save(updatedResides);
    }
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "ResidesService::findAll", allEntries = true),
            @CacheEvict(value = "ResidesService::findOne", key = "#id")
    })
    public void delete(int id){
        residesRepository.deleteById(id);
    }
    public int getResultPrice(Resides resides){
        long days = DAYS.between(resides.getDateIn(),resides.getDateOut());
        int price = (roomService.findOne(resides.getIdRoom())).getPrice();
        return  price * (int) days;
    }

    public int dateValidation(Resides inResides){
        List<Resides> residesList = residesRepository.findByIdRoom(inResides.getIdRoom());
        LocalDate start = inResides.getDateIn();
        LocalDate end = inResides.getDateOut();
        int result = 0;
        if (residesList.isEmpty()){
            result = 1;
        } else {
            for (Resides res : residesList) {
                if (res.getId() != inResides.getId()) {


                    if ((start.isAfter(res.getDateIn()) || start.equals(res.getDateIn())) && (start.isBefore(res.getDateOut())
                            || start.equals(res.getDateOut())) || (end.isAfter(res.getDateIn()) || end.equals(res.getDateIn()))
                            && (end.isBefore(res.getDateOut()) || end.equals(res.getDateOut()))) {
                        result = 0;
                        break;
                    } else {
                        result = 1;
                    }



                } else {
                    result = 1;
                }
            }
        }
       return result;
    }

    public List<Resides> findByYear(int year){
        List<Resides> resides = residesRepository.findAll();

        List<Resides> resultResides = new ArrayList<>();
        for (Resides res : resides) {
            if (res.getDateIn().getYear() == year || res.getDateOut().getYear() == year){
                resultResides.add(res);
            }
        }
        return resultResides;
    }

}
