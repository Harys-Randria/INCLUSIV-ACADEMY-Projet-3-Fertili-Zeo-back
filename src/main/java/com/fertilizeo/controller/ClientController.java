package com.fertilizeo.controller;

import com.fertilizeo.entity.Client;
import com.fertilizeo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/client")
public class ClientController {
    @Autowired
    ClientService clientService;

    @PutMapping("/modifier/{id}")
    public ResponseEntity<Client> updateClient (@RequestBody Client client, @PathVariable long id){
         clientService.updateClient(client, id);
         return  ResponseEntity.ok().body(clientService.updateClient(client,id));
    }
}
