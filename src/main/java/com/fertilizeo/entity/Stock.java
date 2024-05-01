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


    @ManyToOne
    @JoinColumn(name = "compte_id")
    private Compte compte;


    @OneToOne
    @JoinColumn(name = "produit_id", referencedColumnName = "idproduit")
    private Produit produit;

}

