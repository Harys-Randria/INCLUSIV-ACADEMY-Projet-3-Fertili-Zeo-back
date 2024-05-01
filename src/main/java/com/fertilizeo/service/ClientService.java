package com.fertilizeo.service;

import com.fertilizeo.entity.Client;
import com.fertilizeo.entity.Compte;
import com.fertilizeo.entity.Fournisseur;
import com.fertilizeo.repository.ClientRepository;
import com.fertilizeo.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ClientService {
    @Autowired
    public ClientRepository clientRepository;

    @Autowired
    CompteRepository compteRepository;

    public Client addClient (Client client){ clientRepository.save(client);
        return client;
    }

    public Client updateClient( Client client, long id){
        Optional<Compte>clientOptional = compteRepository.findById(id);
        if (clientOptional.isPresent()){
             Compte clientAmodifier=clientOptional.get();
             clientAmodifier.setAddress(client.getAddress());
             clientAmodifier.setName(client.getName());
             clientAmodifier.setEmail(client.getEmail());
             clientAmodifier.setNif_stat(client.getNif_stat());
             clientAmodifier.setPhone(client.getPhone());
             clientAmodifier.setCin(client.getCin());
             compteRepository.save(clientAmodifier);
             return ((Client) clientAmodifier);
        }
        else {
            throw  new EntityNotFoundException("Client non existant, merci de bien vérifier");
        }

    }

    public Client updateClientPassword(Client client, long id, String password){

        Optional<Compte> clientOptional = compteRepository.findById(id);
        if (clientOptional.isPresent()) {
            Compte clientModify = clientOptional.get();
            clientModify.setPassword(password);
            compteRepository.save(clientModify);
            return ((Client) clientModify);

        } else {
            // Gérer le cas où l'entité n'est pas trouvée, par exemple, lancer une exception
            throw new EntityNotFoundException("Entité non trouvée avec l'ID: " + id);
        }

    }

}
