package com.fertilizeo.service;

import com.fertilizeo.entity.Client;
import com.fertilizeo.repository.ClientRepository;
import com.fertilizeo.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    public ClientRepository clientRepository;

    public Client addClient (Client client){ clientRepository.save(client);
        return client;
    }

}
