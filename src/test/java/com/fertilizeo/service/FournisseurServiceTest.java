package com.fertilizeo.service;

import com.fertilizeo.entity.Fournisseur;
import com.fertilizeo.repository.CompteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
public class FournisseurServiceTest {
    @Mock
    CompteRepository compteRepository;

    @InjectMocks
    FournisseurService fournisseurService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testAddFournisseur() {

        Fournisseur fournisseur = new Fournisseur();
        fournisseur.setName("John Doe");
        fournisseur.setCin("154346879128");
        fournisseur.setEmail("john@example.com");
        fournisseur.setPhone("0341236879");


        when(compteRepository.save(fournisseur)).thenReturn(fournisseur);


        Fournisseur result = fournisseurService.addFournisseur(fournisseur);


        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("154346879128",result.getCin());
        assertEquals("0341236879",result.getPhone());
    }
}
