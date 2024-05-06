package com.fertilizeo.entity;

import lombok.Data;

import java.util.List;

@Data
public class StockExportDTO {
    private long productId;
    private String productName;
    private String accountName;
    private int quantity;
    private int stockInitial; // Nouveau champ pour stocker le stock initial
    private int stockFinal; // Nouveau champ pour stocker le stock final
    private String mouvementType;
    private long quantityChanged;
    private List<StockHistory> stockHistory;

    public StockExportDTO(String productName, String accountName, int quantity, long productId, int stockInitial, int stockFinal, String mouvementType,long quantityChanged, List<StockHistory> stockHistory) {
        this.productName = productName;
        this.accountName = accountName;
        this.quantity = quantity;
        this.productId = productId;
        this.stockInitial = stockInitial;
        this.stockFinal = stockFinal;
        this.mouvementType=mouvementType;
        this.quantityChanged=quantityChanged;
        this.stockHistory = stockHistory;
    }
}
