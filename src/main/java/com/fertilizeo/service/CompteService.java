package com.fertilizeo.service;

import com.fertilizeo.entity.Client;
import com.fertilizeo.entity.Compte;
import com.fertilizeo.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompteService {

    @Autowired
    CompteRepository compteRepository;




}
