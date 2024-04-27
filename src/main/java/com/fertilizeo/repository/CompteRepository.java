package com.fertilizeo.repository;

import com.fertilizeo.entity.Client;
import com.fertilizeo.entity.Compte;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompteRepository extends JpaRepository<Compte,Long> {
    public Optional<Compte> findByEmail(String email);



}
