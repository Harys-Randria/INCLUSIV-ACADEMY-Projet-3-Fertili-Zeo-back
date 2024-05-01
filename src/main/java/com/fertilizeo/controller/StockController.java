package com.fertilizeo.controller;

import com.fertilizeo.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/du_produit/{produitId}")
    public ResponseEntity<Long> getStockQuantityByProduitId(@PathVariable Long produitId) {
        Long stockQuantity = stockService.findByProduitIdproduit(produitId);
        if (stockQuantity != null) {
            return ResponseEntity.ok(stockQuantity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
