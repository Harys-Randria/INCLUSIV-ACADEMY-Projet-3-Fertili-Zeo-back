package com.fertilizeo.entity;

import lombok.Data;

@Data
public class StockExportDTO {


    private long productId;
    private String productName;
    private String accountName;
    private int quantity;

    public StockExportDTO(String productName, String accountName, int quantity,long productId) {
        this.productName = productName;
        this.accountName = accountName;
        this.quantity = quantity;
        this.productId=productId;
    }
}


