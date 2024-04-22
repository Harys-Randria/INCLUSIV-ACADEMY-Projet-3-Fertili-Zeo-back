package com.fertilizeo.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@DiscriminatorValue("3")
@Entity
public class Administrateur extends Compte{
}
