package com.fertilizeo.repository;

import com.fertilizeo.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande,Long> {

    public Commande save(Commande commande);
}
