package com.fertilizeo.service;

import com.fertilizeo.entity.Client;
import com.fertilizeo.entity.Producteur;
import com.fertilizeo.repository.CompteRepository;
import com.fertilizeo.repository.ProducteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducteurService {
    @Autowired
    ProducteurRepository producteurRepository;

    public Producteur addProducteur(Producteur producteur){
        producteurRepository.save(producteur);
        return producteur;
    }
}
