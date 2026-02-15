package ecommerce.shipping;

import ecommerce.model.Order;

public interface ShippingMethod {
    void ship(Order order);
    String name();
    double fee();
}
