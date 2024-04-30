package com.fertilizeo.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@DiscriminatorValue("2")
@Entity
public class Fournisseur extends Compte{

    @OneToMany(mappedBy = "fournisseur")
    private List<Produit> produits;

}
