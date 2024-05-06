package com.fertilizeo.service;

import com.fertilizeo.entity.Client;
import com.fertilizeo.entity.Compte;
import com.fertilizeo.entity.Fournisseur;
import com.fertilizeo.entity.Producteur;
import com.fertilizeo.repository.CompteRepository;
import com.fertilizeo.repository.ProducteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ProducteurService {
    @Autowired
    ProducteurRepository producteurRepository;

    @Autowired
    CompteRepository compteRepository;

    public Producteur addProducteur(Producteur producteur){
        producteurRepository.save(producteur);
        return producteur;
    }

    public Producteur updateProducteurPassword(Producteur producteur, long id, String password){

        Optional<Compte> producteurOptional = compteRepository.findById(id);
        if (producteurOptional.isPresent()) {
            Compte producteurModify =producteurOptional.get();
            producteurModify.setPassword(password);
            compteRepository.save(producteurModify);
            return ((Producteur) producteurModify);

        } else {
            // Gérer le cas où l'entité n'est pas trouvée, par exemple, lancer une exception
            throw new EntityNotFoundException("Entité non trouvée avec l'ID: " + id);
        }

    }
}
