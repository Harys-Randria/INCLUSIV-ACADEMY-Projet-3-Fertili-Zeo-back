package com.fertilizeo.service;
import com.fertilizeo.entity.Compte;
import com.fertilizeo.entity.Fournisseur;
import com.fertilizeo.repository.CompteRepository;
import com.fertilizeo.repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class FournisseurService {
    @Autowired
    FournisseurRepository fournisseurRepository;

    @Autowired
    CompteRepository compteRepository;

    public Fournisseur  addFournisseur (Fournisseur fournisseur){
        fournisseurRepository.save(fournisseur);
        return fournisseur;
    }

    public Fournisseur updateFournisseur(Fournisseur fournisseur, long id){

        Optional<Compte> fournisseurOptional = compteRepository.findById(id);
        if (fournisseurOptional.isPresent()) {
            Compte fournisseurModify = fournisseurOptional.get();
            fournisseurModify.setId(id);
            fournisseurModify.setName(fournisseur.getName());
            fournisseurModify.setPhone(fournisseur.getPhone());
            fournisseurModify.setCin(fournisseur.getCin());
            fournisseurModify.setNif_stat(fournisseur.getNif_stat());
            compteRepository.save(fournisseurModify);
            return ((Fournisseur) fournisseurModify);

        } else {
            // Gérer le cas où l'entité n'est pas trouvée, par exemple, lancer une exception
            throw new EntityNotFoundException("Entité non trouvée avec l'ID: " + id);
        }

    }

    public Fournisseur updateFournisseurPassword(Fournisseur fournisseur, long id, String password){

        Optional<Compte> fournisseurOptional = compteRepository.findById(id);
        if (fournisseurOptional.isPresent()) {
            Compte fournisseurModify = fournisseurOptional.get();
            fournisseurModify.setPassword(password);
            compteRepository.save(fournisseurModify);
            return ((Fournisseur) fournisseurModify);

        } else {
            // Gérer le cas où l'entité n'est pas trouvée, par exemple, lancer une exception
            throw new EntityNotFoundException("Entité non trouvée avec l'ID: " + id);
        }

    }

}
