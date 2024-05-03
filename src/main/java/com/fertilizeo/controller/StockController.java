package com.fertilizeo.controller;

import com.fertilizeo.entity.Produit;
import com.fertilizeo.service.ProductService;
import com.fertilizeo.service.StockNotFoundException;
import com.fertilizeo.service.StockService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private  StockService stockService;


    @Autowired
    private ProductService productService;
    @GetMapping("/quantity/{productId}")
    public ResponseEntity<Integer> getStockQuantityByProductId(@PathVariable Long productId) {
        int stockQuantity = stockService.getStockQuantityByProductId(productId);
        return ResponseEntity.ok(stockQuantity);
    }

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

    @PostMapping("/import")
    public ResponseEntity<String> importStockData(@RequestParam("file") MultipartFile file) {
        try {
            stockService.importStockData(file);
            return ResponseEntity.ok().body("Stock data imported successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error importing stock data");
        } catch (StockNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/export/{accountId}")
    public ResponseEntity<byte[]> exportStocksToExcel(@PathVariable Long accountId) {
        try {
            // Appeler la méthode du service pour exporter les stocks liés au compte vers Excel
            stockService.exportStocksByCompteToExcel(accountId);

            // Retourner une réponse OK si l'export est réussi
            return ResponseEntity.ok().body("Export successful".getBytes());
        } catch (IOException e) {
            // Retourner une réponse avec erreur 500 en cas d'échec de l'export
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error exporting stocks data".getBytes());
        }
    }

}
