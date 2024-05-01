package com.fertilizeo.repository;

import com.fertilizeo.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
//    Stock findByProduitIdproduit(Long produitId);

    @Query("SELECT s.quantity FROM Stock s WHERE s.produit.idproduit = :produitId")
    Long findQuantityByProduitId(Long produitId);

}
