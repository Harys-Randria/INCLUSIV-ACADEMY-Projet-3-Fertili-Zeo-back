package com.fertilizeo.controller;

import com.fertilizeo.entity.*;
import com.fertilizeo.repository.CompteRepository;
import com.fertilizeo.repository.PanierRepository;
import com.fertilizeo.service.CommandeService;
import com.fertilizeo.service.ProductService;
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
    CompteRepository compteRepository;

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private PanierRepository panierRepository;

    @Autowired
    ProductService productService;

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

    @PostMapping("/addCommande/{id}")
    public ResponseEntity<Commande> createCommande(@RequestBody List<PanierDto> paniers, @PathVariable Long id) {
        if (paniers == null || paniers.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Commande nouvelleCommande = new Commande();
        nouvelleCommande.setDateCommande(LocalDateTime.now());

        Compte compte = compteRepository.findById(id).orElseThrow(() -> new RuntimeException("Compte non trouvé avec ID : "));

        Commande c = new Commande();
        c.setCompte(compte);
        c.setDateCommande(LocalDateTime.now());

        commandeService.save(c);

        for (PanierDto panier : paniers) {
            Produit prod = productService.getProduitById(panier.getIdproduit());
            Panier panier1 = new Panier();
            panier1.setProduit(prod);
            panier1.setCommande(c);
            panier1.setPrix(panier.getPrice());
            panier1.setNom(panier.getName());
            panier1.setQuantite(panier.getQuantity());
            panier1.setTotal(panier.getQuantity() * panier.getPrice());

            panierRepository.save(panier1);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(c);
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