package com.fertilizeo.controller;

import com.fertilizeo.entity.Fournisseur;
import com.fertilizeo.service.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/fournisseur")
public class FournisseurController {

    @Autowired
    FournisseurService fournisseurService;

    @PutMapping("/modifier/{id}")
    public ResponseEntity<Fournisseur> modifyFournisseur(@RequestBody Fournisseur fournisseur, @PathVariable Long id){
        fournisseurService.updateFournisseur(fournisseur,id);
        return ResponseEntity.ok().body(fournisseurService.updateFournisseur(fournisseur,id));
    }
}
