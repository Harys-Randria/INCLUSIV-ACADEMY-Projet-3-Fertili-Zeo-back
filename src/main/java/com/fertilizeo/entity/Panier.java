package com.fertilizeo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Panier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("quantity")
    private int quantite;

    @JsonProperty("price")
    private int prix;

    @JsonProperty("name")
    private String nom;

    @JsonProperty("somme")
    private double total;

    @ManyToOne
    @JoinColumn(name = "idproduit")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;

    public void updateStockQuantity() {
        if (produit != null && quantite > 0) {
            Stock stock = produit.getStock();
            if (stock != null) {
                int updatedQuantity = stock.getQuantity() - quantite;
                stock.setQuantity(updatedQuantity);
            }
        }
    }
}