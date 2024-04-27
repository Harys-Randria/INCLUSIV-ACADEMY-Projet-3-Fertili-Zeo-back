package com.fertilizeo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idproduit;

    private String name;
    private Double price;
    private LocalDate expirationDate;
    private String type;
    private String category;
    private String description;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "id_compte")
    private Compte compte;
}
