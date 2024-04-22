package com.fertilizeo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "compte_type",discriminatorType = DiscriminatorType.INTEGER)
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String nif_stat;
    private String cin;
    @Transient
    private Integer type;


    // Hacher le mot de passe et le stocker dans l'attribut password
    public void setPassword(String password) {

        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Vérifier si le mot de passe correspond au hachage
    public boolean checkPassword(String password) {

        return BCrypt.checkpw(password, this.password);
    }



}
