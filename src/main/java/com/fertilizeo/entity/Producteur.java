package com.fertilizeo.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@DiscriminatorValue("4")
@Entity
public class Producteur extends Compte {

    @OneToMany(mappedBy = "producteur")
    private List<Produit> produits;

}

