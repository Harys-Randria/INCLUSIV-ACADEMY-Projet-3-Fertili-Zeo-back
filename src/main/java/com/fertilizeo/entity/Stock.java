package com.fertilizeo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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