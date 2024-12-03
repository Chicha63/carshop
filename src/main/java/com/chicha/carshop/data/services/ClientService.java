package com.chicha.carshop.data.services;

import com.chicha.carshop.data.Client;
import com.chicha.carshop.data.repos.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return this.clientRepository.findAll();
    }

    public Optional<Client> findById(int id) {
        return this.clientRepository.findById(id);
    }

    public Client save(Client client) {
        return this.clientRepository.save(client);
    }

    public void deleteById(int id) {
        this.clientRepository.deleteById(id);
    }

    public Client update(Client newClient, int id) {
        return clientRepository.findById(id)
                .map(client -> {
                    client = newClient;
                    return clientRepository.save(client);
                })
                .orElseGet(() -> clientRepository.save(newClient));

    }

}
