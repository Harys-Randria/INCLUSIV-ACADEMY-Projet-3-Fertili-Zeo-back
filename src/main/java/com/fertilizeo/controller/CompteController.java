package com.fertilizeo.controller;


import com.fertilizeo.entity.Client;
import com.fertilizeo.entity.Compte;
import com.fertilizeo.entity.Fournisseur;
import com.fertilizeo.entity.Producteur;
import com.fertilizeo.repository.CompteRepository;
import com.fertilizeo.service.ClientService;
import com.fertilizeo.service.CompteService;
import com.fertilizeo.service.FournisseurService;
import com.fertilizeo.service.ProducteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.fertilizeo.config.jwt.JwtUtils;
import com.fertilizeo.controller.request.LoginRequest;
import com.fertilizeo.controller.response.JwtResponse;
import com.fertilizeo.service.impl.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/compte")
public class CompteController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CompteRepository compteRepository;

    @Autowired
    CompteService compteService;

    @Autowired
    ClientService clientService;

    @Autowired
    FournisseurService fournisseurService;

    @Autowired
    ProducteurService producteurService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/add/users")
    public void addCompte (@RequestBody Compte compte) throws IllegalAccessException {

        if (compte.getType()==1){
            Client client= new Client();
            client.setName(compte.getName());
            client.setPhone(compte.getPhone());
            client.setCin(compte.getCin());
            client.setEmail(compte.getEmail());

            client.setPassword(encoder.encode(compte.getPassword()));
            client.setAddress(compte.getAddress());
            client.setNif_stat(compte.getNif_stat());
            clientService.addClient(client);
        } else if (compte.getType()==2){
            Fournisseur fournisseur= new Fournisseur();
            fournisseur.setName(compte.getName());
            fournisseur.setPhone(compte.getPhone());
            fournisseur.setCin(compte.getCin());
            fournisseur.setEmail(compte.getEmail());
            fournisseur.setPassword(encoder.encode(compte.getPassword()));
            fournisseur.setAddress(compte.getAddress());
            fournisseur.setNif_stat(compte.getNif_stat());
            fournisseurService.addFournisseur(fournisseur);
        } else if (compte.getType()==4) {
            Producteur producteur= new Producteur();
            producteur.setName(compte.getName());
            producteur.setPhone(compte.getPhone());
            producteur.setCin(compte.getCin());
            producteur.setEmail(compte.getEmail());
            producteur.setPassword(encoder.encode(compte.getPassword()));
            producteur.setAddress(compte.getAddress());
            producteur.setNif_stat(compte.getNif_stat());
            producteurService.addProducteur(producteur);
        }


    }


    @PostMapping ("/signin")
    public ResponseEntity<?> authentication(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getCompte().getId(),
                userDetails.getCompte().getName(),
                userDetails.getCompte().getEmail()
        ));
    }


}
