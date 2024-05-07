package com.fertilizeo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PanierDto {

    private Long idproduit;

    private int quantity;

    private int price;

    private String name;
}
