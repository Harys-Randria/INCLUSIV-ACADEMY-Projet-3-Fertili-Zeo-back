package com.fertilizeo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
    private byte[] image;
    private Integer quantity;
    private Integer seuilreapprovisionnement;

    @ManyToOne
    @JoinColumn(name = "id_compte")
    private Compte compte;
    @JsonManagedReference
    @OneToOne(fetch = FetchType.LAZY) // Assurez-vous que le fetch type est correctement configuré
    @JoinColumn(name = "id_stock")
    private Stock stock;


    @OneToMany(mappedBy = "produit")
    private List<Panier> Panier;

}
