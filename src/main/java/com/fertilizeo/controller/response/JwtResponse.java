package com.fertilizeo.controller.response;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private  String cin;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String nif_stat;
    private byte[] photo;

    public JwtResponse(String accessToken, Long id, String name, String email, String cin, String phone, String address, String nif_stat,byte[] photo) {
        this.token = accessToken;
        this.id = id;
        this.name = name;
        this.email = email;
        this.cin = cin;
        this.phone = phone;
        this.address = address;
        this.nif_stat = nif_stat;
        this.photo = photo;
    }
}
