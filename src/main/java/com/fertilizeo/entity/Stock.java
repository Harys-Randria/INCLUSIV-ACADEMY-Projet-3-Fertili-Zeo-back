package com.fertilizeo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idstock;

    private Integer quantity;

    @OneToOne
    @JoinColumn(name = "produit_id")
    private Produit produit;


    @ManyToOne
    @JoinColumn(name = "fournisseur_id")
    private Fournisseur fournisseur;

    @ManyToOne
    @JoinColumn(name = "producteur_id")
    private Producteur producteur;
}

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_stock;

    private Long quantity;

    @OneToOne
    @JoinColumn(name = "produit_id", referencedColumnName = "idproduit")
    private Produit produit;

}