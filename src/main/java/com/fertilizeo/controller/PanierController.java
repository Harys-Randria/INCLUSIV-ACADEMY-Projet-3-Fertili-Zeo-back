package com.fertilizeo.controller;

import com.fertilizeo.entity.Panier;
import com.fertilizeo.service.PanierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PanierController {

    @Autowired
    private PanierService panierService;

    @PostMapping
    public Panier createPanier(@RequestBody Panier panier) {
        return panierService.save(panier);
    }

    @GetMapping
    public List<Panier> getAllPaniers() {
        return panierService.findAll();
    }

    @GetMapping("/{id}")
    public Panier getPanierById(@PathVariable Long id) {
        return panierService.findById(id);
    }

    @PutMapping("/{id}")
    public Panier updatePanier(@PathVariable Long id, @RequestBody Panier panier) {
        panier.setId(id);
        return panierService.save(panier);
    }

    @DeleteMapping("/{id}")
    public void deletePanier(@PathVariable Long id) {
        panierService.deleteById(id);
    }

}
