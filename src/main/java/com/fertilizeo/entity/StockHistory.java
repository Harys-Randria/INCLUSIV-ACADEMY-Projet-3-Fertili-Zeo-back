package com.fertilizeo.entity;



import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


    @Data
    @Entity
    public class StockHistory {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String mouvementType;

        @ManyToOne
        @JoinColumn(name = "stock_id")
        private Stock stock;

        private Integer quantityChange;

        @Temporal(TemporalType.TIMESTAMP)
        private Date date;

        private Integer initialStock;

        private Integer finalStock;
    }



