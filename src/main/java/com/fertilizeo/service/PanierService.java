package com.fertilizeo.service;

import com.fertilizeo.entity.Panier;
import com.fertilizeo.repository.PanierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PanierService {

    @Autowired
    private PanierRepository panierRepository;

    public Panier save(Panier panier) {
        return panierRepository.save(panier);
    }

    public List<Panier> findAll() {
        return panierRepository.findAll();
    }

    public Panier findById(Long id) {
        return panierRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        panierRepository.deleteById(id);
    }

}
