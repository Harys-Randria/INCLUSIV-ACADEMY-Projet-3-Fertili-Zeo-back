package com.fertilizeo.service.impl;

import lombok.Data;

@Data
public class ForgotPasswordRequest {

    private String email;

    private String token;

    private String password;
}
