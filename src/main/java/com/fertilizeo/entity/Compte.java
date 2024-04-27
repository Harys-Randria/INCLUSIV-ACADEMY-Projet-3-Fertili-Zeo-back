package com.fertilizeo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "compte_type",discriminatorType = DiscriminatorType.INTEGER)
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcompte;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String nif_stat;
    private String cin;
    @Transient
    private Integer type=1;
    private boolean isEnable;

    @OneToMany(mappedBy = "compte")
    private List<Produit> produits;


    // Hacher le mot de passe et le stocker dans l'attribut password




}
