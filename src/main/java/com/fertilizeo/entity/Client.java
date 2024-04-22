package com.fertilizeo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

@Entity
@DiscriminatorValue("1")
public class Client extends Compte {
}
