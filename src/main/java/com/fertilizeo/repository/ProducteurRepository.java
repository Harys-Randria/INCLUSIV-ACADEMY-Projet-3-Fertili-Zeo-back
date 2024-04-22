package com.fertilizeo.repository;

import com.fertilizeo.entity.Producteur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProducteurRepository extends JpaRepository<Producteur,Long> {
}
