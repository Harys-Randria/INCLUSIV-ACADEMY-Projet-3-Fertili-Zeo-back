package com.fertilizeo.service;


import com.fertilizeo.entity.Produit;
import com.fertilizeo.entity.Stock;
import com.fertilizeo.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public int getQuantiteEnStock(Produit produit) {
        Optional<Stock> stockOption = stockRepository.findByProduit(produit);
        return stockOption.map(Stock::getQuantity).orElse(0);
    }

    public void augmenterStock(Produit produit, Integer quantity) {
        Optional<Stock> stockOption = stockRepository.findByProduit(produit);
        Stock stock = stockOption.orElse(new Stock());
        stock.setProduit(produit);
        stock.setQuantity(stock.getQuantity() + quantity);
        stockRepository.save(stock);
    }

    public void diminuerStock(Produit produit, Integer quantity) {

        Optional<Stock> stockOption = stockRepository.findByProduit(produit);
        Stock stock = stockOption.orElseThrow(() -> new IllegalArgumentException("Stock introuvable"));

        Integer currentquantity = stock.getQuantity();
        if (currentquantity < quantity) {
            throw new IllegalArgumentException("Stock insuffisant");
        }
        stock.setQuantity(currentquantity - quantity);
        stockRepository.save(stock);
    }


    public void reajusterStock(Produit produit) {
        // Logique pour réajuster le stock du produit avant d'effectuer une opération

        // Vérification des seuils de réapprovisionnement
        if (produit.getQuantity() < produit.getSeuilreapprovisionnement()) {
            //  méthode pour réapprovisionner le produit à appeler

        }

        // Autres ajustements spécifiques selon les besoins FERTILI'ZEO
    }


}
