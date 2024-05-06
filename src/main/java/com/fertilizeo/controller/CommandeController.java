package com.fertilizeo.controller;

import com.fertilizeo.entity.Commande;
import com.fertilizeo.entity.Panier;
import com.fertilizeo.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @PutMapping("/{id}")
    public Commande updateCommande(@PathVariable Long id, @RequestBody Commande commande) {
        commande.setId(id);
        return commandeService.save(commande);
    }

    @DeleteMapping("/{id}")
    public void deleteCommande(@PathVariable Long id) {
        commandeService.deleteById(id);
    }

    @PostMapping("/ajoutCommande")
    public Commande ajouterCommande(@RequestBody List<Panier> paniers) {
        Commande nouvelleCommande = new Commande();
        nouvelleCommande.setDateCommande(LocalDateTime.now());
        nouvelleCommande.setPaniers(paniers);

        for (Panier panier : paniers) {
            panier.setCommande(nouvelleCommande);
        }

        return commandeService.save(nouvelleCommande);
    }

    @PostMapping("/addCommande")
    public ResponseEntity<Commande> createCommande(@RequestBody List<Panier> paniers) {
        if (paniers == null || paniers.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Commande nouvelleCommande = new Commande();
        nouvelleCommande.setDateCommande(LocalDateTime.now());
        nouvelleCommande.setPaniers(paniers);

        for (Panier panier : paniers) {
            panier.setCommande(nouvelleCommande);
        }
//
//        // Mettre à jour l'ID du compte dans la session ID
//        Long compteId = paniers.get(0).getCompte().getIdcompte();
//        nouvelleCommande.setCompte(new Compte(compteId));

        Commande savedCommande = commandeService.save(nouvelleCommande);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCommande);
    }

    @GetMapping
    public ResponseEntity<List<Commande>> getAllCommandes() {
        List<Commande> commandes = commandeService.findAll();
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable Long id) {
        Commande commande = commandeService.findById(id);
        if (commande != null) {
            return ResponseEntity.ok(commande);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}