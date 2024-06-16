package ru.mpryadihin.diplom.servicies;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mpryadihin.diplom.models.Client;
import ru.mpryadihin.diplom.repositories.ClientRepository;
import ru.mpryadihin.diplom.util.CryptoUtils;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class ClientService {

    private final ClientRepository clientRepository;

    private final CryptoUtils cryptoUtils;

    private final SecretKeyService secretKeyService;

    @Autowired
    public ClientService(ClientRepository clientRepository, CryptoUtils cryptoUtils, SecretKeyService secretKeyService) {
        this.clientRepository = clientRepository;
        this.cryptoUtils = cryptoUtils;
        this.secretKeyService = secretKeyService;
    }
    @Cacheable(value = "ClientService::findAll")
    public List<Client> findAll(){
        return clientRepository.findAll();
    }
    @Cacheable(value = "ClientService::findOne", key = "#id")
    public Client findOne(int id){
        Optional<Client> foundClient = clientRepository.findById(id);
        foundClient.ifPresent(client -> client.setPassport(cryptoUtils.decrypt(client.getPassport(), secretKeyService.getKey())));
        return foundClient.orElse(null);
    }
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "ClientService::findAll", allEntries = true),
            @CacheEvict(value = "ClientService::findOne", key = "#client.id")
    })
    public void save(Client client)  {
        try {
            client.setPassport(cryptoUtils.encrypt(client.getPassport(), secretKeyService.getKey()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        clientRepository.save(client);
    }
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "ClientService::findAll", allEntries = true),
            @CacheEvict(value = "ClientService::findOne", key = "#id")
    })
    public void update(int id, Client updatedClient){
        updatedClient.setId(id);
        updatedClient.setPassport(cryptoUtils.encrypt(updatedClient.getPassport(), secretKeyService.getKey()));
        clientRepository.save(updatedClient);
    }
    @Transactional(readOnly = false)
    @Caching(evict = {
            @CacheEvict(value = "ClientService::findAll", allEntries = true),
            @CacheEvict(value = "ClientService::findOne", key = "#id")
    })
    public void delete(int id){
        clientRepository.deleteById(id);
    }
    public List<Client> search(String s){
        List<Client> searchResult = clientRepository.searchClientBySurname(s);
         if (searchResult.isEmpty()) {
             Client notFound = new Client();
             notFound.setSurname("Не найдено");
             notFound.setName("");
             notFound.setPatronymic("");
             searchResult.add(notFound);
         }
        return searchResult;
    }
}
