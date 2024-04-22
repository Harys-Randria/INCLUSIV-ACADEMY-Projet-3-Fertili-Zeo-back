package com.fertilizeo.controller;

import com.fertilizeo.entity.Client;
import com.fertilizeo.entity.Compte;
import com.fertilizeo.entity.Fournisseur;
import com.fertilizeo.entity.Producteur;
import com.fertilizeo.service.ClientService;
import com.fertilizeo.service.CompteService;
import com.fertilizeo.service.FournisseurService;
import com.fertilizeo.service.ProducteurService;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/compte")
public class CompteController {

    @Autowired
    CompteService compteService;

    @Autowired
    ClientService clientService;

    @Autowired
    FournisseurService fournisseurService;

    @Autowired
    ProducteurService producteurService;
    @PostMapping("/add/users")
    public void addCompte (@RequestBody Compte compte) throws IllegalAccessException {


    if (compte instanceof Client){ clientService.addClient((Client) compte);
      }
      else if (compte instanceof Fournisseur) {
          fournisseurService.addFournisseur((Fournisseur) compte);

      } else if (compte instanceof Producteur) {
          producteurService.addProducteur((Producteur) compte);
      } else {
          throw new IllegalAccessException("Erreur sur le type de compte");
      }

    }




}
