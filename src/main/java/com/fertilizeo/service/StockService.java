package com.fertilizeo.service;

import com.fertilizeo.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Long> getStocksByProduitId(Long produitId) {
        return stockRepository.findByProduitIdproduit(produitId);
    }

}
