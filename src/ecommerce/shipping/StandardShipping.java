package ecommerce.shipping;

import ecommerce.model.Order;

public class StandardShipping implements ShippingMethod {
    @Override 
    public void ship(Order order) {
        System.out.println("📦 Expédition STANDARD de la commande " + order.getId());
    }
    
    @Override 
    public double fee() { 
        return 4.99; 
    }
    @Override 
    public String name() { 
        return "Standard"; 
    }
}
