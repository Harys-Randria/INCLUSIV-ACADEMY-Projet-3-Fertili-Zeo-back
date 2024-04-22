package com.fertilizeo.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("2")
@Entity
public class Fournisseur extends Compte{



}
