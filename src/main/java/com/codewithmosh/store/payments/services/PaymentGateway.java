package com.codewithmosh.store.payments.services;

import com.codewithmosh.store.order.entities.Order;
import com.codewithmosh.store.payments.dtos.CheckoutSession;
import com.codewithmosh.store.payments.dtos.PaymentResult;
import com.codewithmosh.store.payments.dtos.WebhookRequest;

import java.util.Optional;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);
    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}
