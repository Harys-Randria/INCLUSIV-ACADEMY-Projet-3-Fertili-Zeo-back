package com.fertilizeo.repository;

import com.fertilizeo.entity.Compte;
import com.fertilizeo.entity.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FournisseurRepository extends JpaRepository<Fournisseur, Long> {

}
