package com.fertilizeo.service;

import com.fertilizeo.entity.Compte;
import com.fertilizeo.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompteService {

    @Autowired
    CompteRepository compteRepository;

    public boolean findEmail(String email){
        Optional<Compte> compte = compteRepository.findByEmail(email);
    return compte.isPresent();
    }


    public Optional<Compte> findEmail1(String email) {
        Optional<Compte> compte = compteRepository.findByEmail(email);
        return compte;
    }




}
