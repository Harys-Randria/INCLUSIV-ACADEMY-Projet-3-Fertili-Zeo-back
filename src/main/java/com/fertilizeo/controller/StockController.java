package com.fertilizeo.controller;

import com.fertilizeo.entity.Produit;
import com.fertilizeo.service.ProductService;
import com.fertilizeo.service.StockService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
import com.fertilizeo.repository.StockRepository;
import com.fertilizeo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private StockRepository stockRepository;

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/by-produit/{produitId}")
    public ResponseEntity<List<Long>> getStocksByProduitId(@PathVariable Long produitId) {
        List<Long> stocks = stockService.getStocksByProduitId(produitId);
        return ResponseEntity.ok(stocks);
    }
    private  StockService stockService;


    @Autowired
    private ProductService productService;

    @GetMapping("/{produitId}")
    public ResponseEntity<Integer> getQuantiteEnStock(@PathVariable Long idproduit) {
        Produit produit = productService.getProduitById(idproduit);
        if (produit != null) {

            int quantiteEnStock = stockService.getQuantiteEnStock(produit);
            return ResponseEntity.ok(quantiteEnStock);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/augmenter/{produitId}")
    public ResponseEntity<Void> augmenterStock(@PathVariable Long idproduit, @RequestParam Integer quantity) {
        Produit produit = productService.getProduitById(idproduit);
        if (produit != null) {

            stockService.augmenterStock(produit, quantity);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/diminuer/{produitId}")
    public ResponseEntity<Void> diminuerStock(@PathVariable Long idproduit, @RequestParam Integer quantity) {
        Produit produit = productService.getProduitById(idproduit);
        if (produit != null) {


            stockService.diminuerStock(produit, quantity);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
