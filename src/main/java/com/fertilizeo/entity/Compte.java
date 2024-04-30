package com.fertilizeo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private boolean is_delete = false;
    @Transient
    private Integer type=1;
    @Transient
    private String resetToken;
    private boolean isEnable;

    @JsonIgnore
    @OneToMany(mappedBy = "compte")
    private List<Produit> produits;


}
