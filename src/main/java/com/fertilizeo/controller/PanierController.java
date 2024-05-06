package com.fertilizeo.controller;

import com.fertilizeo.entity.Commande;
import com.fertilizeo.entity.Panier;
import com.fertilizeo.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/panier")
public class PanierController {

    @Autowired
    private CommandeRepository commandeRepository;

    @PostMapping("/lignepanier")
    public void savePanier(@RequestBody Panier[] paniers) {
        try {
            // Créer une nouvelle commande
            Commande nouvelleCommande = new Commande();
            nouvelleCommande.setDateCommande(LocalDateTime.now());

            // Associer la liste des paniers à la commande
            nouvelleCommande.setPaniers(Arrays.asList(paniers));

            // Enregistrer la nouvelle commande dans la base de données
            commandeRepository.save(nouvelleCommande);

            System.out.println("Nouvelle commande créée : " + nouvelleCommande);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
