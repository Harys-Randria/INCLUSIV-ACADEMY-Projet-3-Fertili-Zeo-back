package com.fertilizeo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateCommande;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "commande")
    private List<Panier> paniers;

    public Commande(List<Panier> paniers) {
        this.paniers = paniers;

        for (Panier panier : paniers) {
            panier.setCommande(this);
        }
    }

    @JsonProperty("compteId") // Spécifiez le nom de la propriété sérialisée
    @ManyToOne
    private Compte compte;




}