package com.fertilizeo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    @JoinColumn(name = "produit_id", referencedColumnName = "idproduit")
    private Produit produit;

}
