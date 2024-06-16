package ru.mpryadihin.diplom.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mpryadihin.diplom.models.Job;
import ru.mpryadihin.diplom.repositories.JobRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

   @Cacheable(value = "JobService::findAll")
    public List<Job> findAll() {
        return jobRepository.findAll();
    }
    @Cacheable(value = "JobService::findOne", key = "#id")
    public Job findOne(int id){
        Optional<Job> foundedJob = jobRepository.findById(id);
        return foundedJob.orElse(null);
    }
    @Caching(evict = {
            @CacheEvict(value = "JobService::findAll", allEntries = true),
            @CacheEvict(value = "JobService::findOne", key = "#job.id")
    })
    @Transactional(readOnly = false)
    public void save(Job job){
        jobRepository.save(job);
    }
    @Caching(evict = {
            @CacheEvict(value = "JobService::findAll", allEntries = true),
            @CacheEvict(value = "JobService::findOne", key = "#id")
    })
    @Transactional(readOnly = false)
    public void update(int id, Job updatedJob){
        updatedJob.setId(id);
        jobRepository.save(updatedJob);
    }
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "JobService::findAll", allEntries = true),
            @CacheEvict(value = "JobService::findOne", key = "#id")
    })
    public void delete(int id){
        jobRepository.deleteById(id);
    }

}
