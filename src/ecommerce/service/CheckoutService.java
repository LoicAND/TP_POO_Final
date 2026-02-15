package ecommerce.service;

import ecommerce.model.Order;
import ecommerce.payment.PaymentMethod;
import ecommerce.shipping.ShippingMethod;

public class CheckoutService {
    private final OrderService orderService;

    public CheckoutService(OrderService orderService) {
        this.orderService = orderService;
    }

    public double totalWithShipping(Order order, ShippingMethod shipping) {
        return orderService.computeItemsTotal(order) + shipping.fee();
    }

    public void pay(Order order, PaymentMethod method, double amount) {
        method.pay(amount);
        orderService.markPaid(order);
    }

    public void ship(Order order, ShippingMethod shipping) {
        shipping.ship(order);
        orderService.markShipped(order);
    }
}
