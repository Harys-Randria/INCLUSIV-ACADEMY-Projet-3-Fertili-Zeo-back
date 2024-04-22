package com.fertilizeo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCrypt;


@Entity
@DiscriminatorValue("1")
public class Client extends Compte {





}
