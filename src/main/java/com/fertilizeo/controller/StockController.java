package com.fertilizeo.controller;

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
}
