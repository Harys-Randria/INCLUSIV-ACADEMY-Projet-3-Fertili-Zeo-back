package com.fertilizeo.service;

import com.fertilizeo.entity.Client;
import com.fertilizeo.entity.Compte;
import com.fertilizeo.repository.CompteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public Optional<Compte> findById (long id){
        Optional<Compte> compte= compteRepository.findById(id);
        return compte;
    }

    public Compte updateById (long id, MultipartFile photo,Compte compte) throws IOException {
        Optional<Compte> compteOptional = compteRepository.findById(id);
        if (compteOptional.isPresent()) {
            Compte compteModify = compteOptional.get();
            compteModify.setName(compte.getName());
            compteModify.setPhone(compte.getPhone());
            compteModify.setCin(compte.getCin());
            compteModify.setNif_stat(compte.getNif_stat());
            compteModify.setEmail(compte.getEmail());
            compteModify.setAddress(compte.getAddress());
            compteModify.setPhoto(photo.getBytes());
            compteRepository.save(compteModify);
            return compteModify;
        }
        return null;
    }




}




