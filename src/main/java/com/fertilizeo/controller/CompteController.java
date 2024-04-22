package com.fertilizeo.controller;


import com.fertilizeo.entity.Client;
import com.fertilizeo.entity.Compte;
import com.fertilizeo.entity.Fournisseur;
import com.fertilizeo.entity.Producteur;
import com.fertilizeo.service.ClientService;
import com.fertilizeo.service.CompteService;
import com.fertilizeo.service.FournisseurService;
import com.fertilizeo.service.ProducteurService;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fertilizeo.config.jwt.JwtUtils;
import com.fertilizeo.controller.request.LoginRequest;
import com.fertilizeo.controller.response.JwtResponse;
import com.fertilizeo.repository.CompteRepository;
import com.fertilizeo.service.impl.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/compte")
public class CompteController {
    AuthenticationManager authenticationManager;

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


    if (compte instanceof Client){ clientService.addClient((Client) compte);
      }
      else if (compte instanceof Fournisseur) {
          fournisseurService.addFournisseur((Fournisseur) compte);

      } else if (compte instanceof Producteur) {
          producteurService.addProducteur((Producteur) compte);
      } else {
          throw new IllegalAccessException("Erreur sur le type de compte");
      }

    }


    @PostMapping("/signin")
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
