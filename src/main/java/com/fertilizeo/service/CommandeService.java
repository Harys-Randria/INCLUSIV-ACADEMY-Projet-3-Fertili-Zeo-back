package com.fertilizeo.service;

import com.fertilizeo.entity.Commande;
import com.fertilizeo.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    public Commande save(Commande commande) {
        return commandeRepository.save(commande);
    }

    public List<Commande> findAll() {
        return commandeRepository.findAll();
    }

    public Commande findById(Long id) {
        return commandeRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        commandeRepository.deleteById(id);
    }
}
