package com.fertilizeo.repository;

import com.fertilizeo.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Produit, Long> {

    // Modifier le nom de la méthode pour correspondre à la propriété dans l'entité Produit
    List<Produit> findIdsByCompte_Idcompte(Long idCompte); // Utilisation de "idcompte" au lieu de "id"
}
