package com.fertilizeo.repository;

import com.fertilizeo.entity.Client;
import com.fertilizeo.entity.Compte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte,Long> {
    public Optional<Compte> findByEmail(String email);
}
