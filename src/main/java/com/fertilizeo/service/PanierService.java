package com.fertilizeo.service;

import com.fertilizeo.entity.Panier;
import com.fertilizeo.repository.PanierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PanierService {

    private final PanierRepository panierRepository;

    @Autowired
    public PanierService(PanierRepository panierRepository) {
        this.panierRepository = panierRepository;
    }

    public Panier save(Panier panier) {
        return panierRepository.save(panier);
    }

    public List<Panier> findAll() {
        return panierRepository.findAll();
    }

    public Panier findById(Long id) {
        Optional<Panier> optionalPanier = panierRepository.findById(id);
        return optionalPanier.orElse(null);
    }

    public void deleteById(Long id) {
        panierRepository.deleteById(id);
    }

    public void updatePanier(Long panierId, int nouvelleQuantite) {
        Optional<Panier> optionalPanier = panierRepository.findById(panierId);
        if (optionalPanier.isPresent()) {
            Panier panier = optionalPanier.get();
            panier.setQuantite(nouvelleQuantite);
            panier.updateStockQuantity();
            panierRepository.save(panier);
        }
    }
}