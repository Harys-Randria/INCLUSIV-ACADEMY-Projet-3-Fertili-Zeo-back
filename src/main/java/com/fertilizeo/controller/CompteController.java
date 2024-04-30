package com.fertilizeo.controller;


import com.fertilizeo.config.jwt.JwtTokenValidationUtil;
import com.fertilizeo.config.jwt.JwtUtils;
import com.fertilizeo.controller.request.LoginRequest;
import com.fertilizeo.controller.response.JwtResponse;
import com.fertilizeo.entity.Client;
import com.fertilizeo.entity.Compte;
import com.fertilizeo.entity.Fournisseur;
import com.fertilizeo.entity.Producteur;
import com.fertilizeo.repository.CompteRepository;
import com.fertilizeo.service.*;


import com.fertilizeo.service.impl.ForgotPasswordRequest;
import com.fertilizeo.service.impl.UserDetailsImpl;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;
import java.util.UUID;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
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

    @Autowired
    JwtTokenValidationUtil jwtTokenValidationUtil;

    @Autowired
    EmailSenderService emailSenderService;



    @PostMapping("/add/users")
    public ResponseEntity<?> addCompte(@RequestBody Compte compte) {


        //verification si l'email est deja utiliser par un autre compte existant
        if (compteService.findEmail(compte.getEmail())) {
            return ResponseEntity.badRequest().body("Erreur : Un compte est déjà inscrit avec l'email fourni.");
        }
        try {
            if (compte.getType() == 2) {
                Fournisseur fournisseur = new Fournisseur();
                fournisseur.setName(compte.getName());
                fournisseur.setPhone(compte.getPhone());
                fournisseur.setCin(compte.getCin());
                fournisseur.setEmail(compte.getEmail());
                fournisseur.setPassword(encoder.encode(compte.getPassword()));
                fournisseur.setAddress(compte.getAddress());
                fournisseur.setNif_stat(compte.getNif_stat());
                fournisseur.setEnable(false);

                fournisseurService.addFournisseur(fournisseur);
                emailSenderService.sendEmail(fournisseur.getEmail(), fournisseur.getName());
            } else if (compte.getType() == 4) {
                Producteur producteur = new Producteur();
                producteur.setName(compte.getName());
                producteur.setPhone(compte.getPhone());
                producteur.setCin(compte.getCin());
                producteur.setEmail(compte.getEmail());
                producteur.setPassword(encoder.encode(compte.getPassword()));
                producteur.setAddress(compte.getAddress());
                producteur.setNif_stat(compte.getNif_stat());
                producteur.setEnable(false);
                producteurService.addProducteur(producteur);
                emailSenderService.sendEmail(producteur.getEmail(), producteur.getName());
            } else {
                Client client = new Client();
                client.setName(compte.getName());
                client.setPhone(compte.getPhone());
                client.setCin(compte.getCin());
                client.setEmail(compte.getEmail());
                if (compte.getPassword() == null) {
                    compte.setPassword(null);
                } else {
                    client.setPassword(encoder.encode(compte.getPassword()));
                }
                client.setAddress(compte.getAddress());
                client.setNif_stat(compte.getNif_stat());
                client.setEnable(false);
                clientService.addClient(client);
                emailSenderService.sendEmail(client.getEmail(), client.getName());
            }

            return ResponseEntity.ok("Inscription réussie. Veuillez vérifier votre email pour activer votre compte.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur lors de l'inscription : " + e.getMessage());
        }
    }


    //Authentifaction
    @PostMapping("/signin")

    public ResponseEntity<?> authentication(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();




            return ResponseEntity.ok(new JwtResponse(jwt,
                    userDetails.getCompte().getIdcompte(),
                    userDetails.getCompte().getName(),
                    userDetails.getCompte().getEmail(),
                    userDetails.getCompte().getCin(),
                    userDetails.getCompte().getPhone(),
                    userDetails.getCompte().getAddress(),
                    userDetails.getCompte().getNif_stat()
            ));

        }

        //verification si le compte est active
        catch (Exception exception) {

            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }


    //Activation de compte via liens envoyer dans la boite e-mail
    @GetMapping("/verify")
    public ResponseEntity<?> verifyAccount(@RequestParam String token) {
        String email = jwtTokenValidationUtil.extractEmail(token);
        if (!jwtTokenValidationUtil.validateToken(token, email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Le lien est invalide ou expiré");
        }

        // Récupérer le compte associé à l'email
        Optional<Compte> compteOpt = compteRepository.findByEmail(email);
        if (compteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aucun compte trouvé avec cet email.");
        }

        Compte compte = compteOpt.get();

        // Vérifier si le compte est déjà activé
        if (compte.isEnable()) {
            return ResponseEntity.ok("Le compte est déjà activé.");
        }

        // Activer le compte
        compte.setEnable(true);
        compteRepository.save(compte);
        String frontEndUrl = "http://localhost:3000";
        String redirectUrl = ServletUriComponentsBuilder.fromUriString(frontEndUrl)
                .path("/")
                .build()
                .toUriString();

        // Renvoyer une réponse de redirection avec le code HTTP 302
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", redirectUrl)
                .body("Compte activé avec succès. Redirection vers " + redirectUrl);
    }




    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteUserAccountById(@PathVariable Long accountId) {
        try {
            compteRepository.deleteById(accountId);
            return new ResponseEntity<>("Compte utilisateur supprimé avec succès", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la suppression du compte utilisateur : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{accountId}/soft-delete")
    public ResponseEntity<String> softDeleteUserAccountById(@PathVariable Long accountId) {
        try {

            Compte compte = compteRepository.findById(accountId)
                    .orElseThrow(() -> new RuntimeException("Compte non trouvé avec ID : " + accountId));


            compte.set_delete(true);


            compteRepository.save(compte);

            return new ResponseEntity<>("Compte utilisateur marqué comme supprimé avec succès", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la mise à jour du compte utilisateur : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}



