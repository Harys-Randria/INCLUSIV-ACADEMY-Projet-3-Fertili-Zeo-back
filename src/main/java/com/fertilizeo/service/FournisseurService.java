package com.fertilizeo.service;

import com.fertilizeo.entity.Fournisseur;
import com.fertilizeo.repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FournisseurService {
    @Autowired
    FournisseurRepository fournisseurRepository;

    public Fournisseur  addFournisseur (Fournisseur fournisseur){
        fournisseurRepository.save(fournisseur);
        return fournisseur;
    }


}
