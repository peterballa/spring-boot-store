package com.codewithmosh.store.payments.services;

import com.codewithmosh.store.auth.services.AuthService;
import com.codewithmosh.store.cart.exceptions.CartEmptyException;
import com.codewithmosh.store.cart.exceptions.CartNotFoundException;
import com.codewithmosh.store.cart.repositories.CartRepository;
import com.codewithmosh.store.cart.services.CartService;
import com.codewithmosh.store.order.entities.Order;
import com.codewithmosh.store.order.repositories.OrderRepository;
import com.codewithmosh.store.payments.dtos.CheckoutRequest;
import com.codewithmosh.store.payments.dtos.CheckoutResponse;
import com.codewithmosh.store.payments.dtos.WebhookRequest;
import com.codewithmosh.store.payments.exceptions.PaymentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;

    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);

        if (cart == null) {
            throw new CartNotFoundException();
        }

        if (cart.isEmpty()) {
            throw new CartEmptyException();
        }

        var order = Order.fromCart(cart, authService.getCurrentUser());

        orderRepository.save(order);

        try {
            var session = paymentGateway.createCheckoutSession(order);

            cartService.clearCart(cart.getId());

            return new CheckoutResponse(order.getId(), session.getCheckoutUrl());
        } catch (PaymentException exception) {
            System.out.println(exception.getMessage());
            orderRepository.delete(order);
            throw exception;
        }
    }

    public void handleWebhookEvent(WebhookRequest webhookRequest) {
        paymentGateway.parseWebhookRequest(webhookRequest).ifPresent(paymentResult -> {
            var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
            order.setStatus(paymentResult.getPaymentStatus());
            orderRepository.save(order);
        });

    }


}
