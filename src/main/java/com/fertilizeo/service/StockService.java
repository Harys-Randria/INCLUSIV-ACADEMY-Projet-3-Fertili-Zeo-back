package com.fertilizeo.service;

import com.fertilizeo.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    /*public Stock findByProduitIdproduit(Long produitId) {
        Stock stock = stockRepository.findByProduitIdproduit(produitId);
        return stock != null ? stock.getQuantity() : 0L;
    }*/
    public Long findByProduitIdproduit(Long produitId) {
        return stockRepository.findQuantityByProduitId(produitId);
    }

}