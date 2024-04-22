package com.fertilizeo.service;

import com.fertilizeo.entity.Client;
import com.fertilizeo.entity.Producteur;
import com.fertilizeo.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducteurService {
    @Autowired
    CompteRepository compteRepository;

    public Producteur addProducteur(Producteur producteur){
        compteRepository.save(producteur);
        return producteur;
    }
}
