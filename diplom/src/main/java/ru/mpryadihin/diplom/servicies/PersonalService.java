package ru.mpryadihin.diplom.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.mpryadihin.diplom.models.Personal;
import ru.mpryadihin.diplom.repositories.PersonalRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonalService {

    private final PersonalRepository personalRepository;

    @Autowired
    public PersonalService(PersonalRepository personalRepository) {
        this.personalRepository = personalRepository;
    }

    @Cacheable(value = "PersonalService::findAll")
    public List<Personal> findAll(){
        return personalRepository.findAll();
    }
    public Personal findOne(int id){
        Optional<Personal> foundedPersonal = personalRepository.findById(id);
        return foundedPersonal.orElse(null);
    }
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "PersonalService::findAll", allEntries = true),
    })
    public void save(Personal personal, MultipartFile file) throws IOException {
        byte[] imageByte = file.getBytes();
        personal.setImage(imageByte);
        personalRepository.save(personal);

    }
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "PersonalService::findAll", allEntries = true),
    })
    public void update(int id, Personal updatedPersonal, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            updatedPersonal.setImage(null);
            updatedPersonal.setId(id);
        } else {
            byte[] imageByte = file.getBytes();
            updatedPersonal.setId(id);
            updatedPersonal.setImage(imageByte);
        }
        personalRepository.save(updatedPersonal);
    }
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "PersonalService::findAll", allEntries = true),
    })
    public void delete(int id){
        personalRepository.deleteById(id);
    }
}
