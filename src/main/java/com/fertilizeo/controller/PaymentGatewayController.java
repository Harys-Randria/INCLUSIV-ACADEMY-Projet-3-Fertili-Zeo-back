package com.fertilizeo.controller;

import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/payment")
public class PaymentGatewayController {

    private StripeClient stripeClient;

    @Autowired
    PaymentGatewayController(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }

    @PostMapping("/charge")
    public Charge chargeCard(@RequestHeader(value="token") String token, @RequestHeader(value="amount") Double amount) throws Exception {
        System.out.println(token);
        System.out.println(amount);
        return this.stripeClient.chargeNewCard(token, amount);

    }

}
