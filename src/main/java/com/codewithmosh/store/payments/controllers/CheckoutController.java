package com.codewithmosh.store.payments.controllers;

import com.codewithmosh.store.cart.exceptions.CartEmptyException;
import com.codewithmosh.store.cart.exceptions.CartNotFoundException;
import com.codewithmosh.store.common.dtos.ErrorDto;
import com.codewithmosh.store.payments.dtos.CheckoutRequest;
import com.codewithmosh.store.payments.dtos.CheckoutResponse;
import com.codewithmosh.store.payments.dtos.WebhookRequest;
import com.codewithmosh.store.payments.exceptions.PaymentException;
import com.codewithmosh.store.payments.services.CheckoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping
    public CheckoutResponse checkout(
            @Valid @RequestBody CheckoutRequest request
    ) {
        return checkoutService.checkout(request);
    }

    @PostMapping("/webhook")
    public void handleWebhook(
            @RequestHeader Map<String, String> headers,
            @RequestBody String payload
    ) {
        checkoutService.handleWebhookEvent(new WebhookRequest(headers, payload));
    }

    @ExceptionHandler({CartNotFoundException.class, CartEmptyException.class})
    public ResponseEntity<ErrorDto> handleException(Exception exception) {
        return ResponseEntity.badRequest().body(new ErrorDto(exception.getMessage()));
    };

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorDto> handlePaymentException(Exception exception) {
        return ResponseEntity.badRequest().body(new ErrorDto(exception.getMessage()));
    }
}
