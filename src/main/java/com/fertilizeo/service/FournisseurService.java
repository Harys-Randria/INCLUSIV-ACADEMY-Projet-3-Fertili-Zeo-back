package com.fertilizeo.service;

import com.fertilizeo.entity.Fournisseur;
import com.fertilizeo.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FournisseurService {
    @Autowired
    CompteRepository compteRepository;

    public Fournisseur  addFournisseur (Fournisseur fournisseur){
        compteRepository.save(fournisseur);
        return fournisseur;
    }
}
