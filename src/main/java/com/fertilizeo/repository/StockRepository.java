package com.fertilizeo.repository;


import com.fertilizeo.entity.Produit;
import com.fertilizeo.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProduit(Produit produit);

    List<Stock> findByCompteIdcompte(Long compteId);

    Optional<Stock> findByProduitIdproduit(Long produitId);

    @Query("SELECT s.quantity FROM Stock s WHERE s.produit.idproduit = :produitId")
    Long findQuantityByProduitId(Long produitId);

}