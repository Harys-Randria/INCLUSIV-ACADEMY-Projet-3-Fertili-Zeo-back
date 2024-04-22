package com.fertilizeo.service;

import com.fertilizeo.entity.Client;
import com.fertilizeo.repository.CompteRepository;
import com.fertilizeo.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ClientServiceTest {

    @Mock
    CompteRepository compteRepository;

    @InjectMocks
    ClientService clientService; // Classe à tester

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Créer un client fictif pour le test
    @Test
    public void testAddClient() {

        Client client = new Client();
        client.setName("John Doe");
        client.setCin("154346879128");
        client.setEmail("john@example.com");
        client.setPhone("0341236879");

        // Simuler le comportement du repository
        when(compteRepository.save(client)).thenReturn(client);

        // Appeler la méthode à tester
        Client result = clientService.addClient(client);

        // Vérifier le résultat
        assertEquals("John Doe", result.getName());
        assertEquals("john@example.com", result.getEmail());
        assertEquals("154346879128",result.getCin());
        assertEquals("0341236879",result.getPhone());
    }
}
