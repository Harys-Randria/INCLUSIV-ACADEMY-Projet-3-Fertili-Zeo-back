package com.fertilizeo.service;
import com.fertilizeo.entity.Client;
import com.fertilizeo.entity.Producteur;
import com.fertilizeo.repository.CompteRepository;
import com.fertilizeo.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProducteurServiceTest {
    @Mock
    CompteRepository compteRepository;

    @InjectMocks
    ProducteurService producteurService; // Classe à tester

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Créer un client fictif pour le test
    @Test
    public void testAddProducteur() {

        Producteur producteur = new Producteur();
        producteur.setName("John Doe");
        producteur.setCin("154346879128");
        producteur.setEmail("john@example.com");
        producteur.setPhone("0341236879");

        // Simuler le comportement du repository
        when(compteRepository.save(producteur)).thenReturn(producteur);

        // Appeler la méthode à tester
        Producteur result = producteurService.addProducteur(producteur);

        // Vérifier le résultat
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("154346879128",result.getCin());
        assertEquals("0341236879",result.getPhone());
    }
}
