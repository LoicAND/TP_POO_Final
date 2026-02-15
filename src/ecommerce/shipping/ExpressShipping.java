package ecommerce.shipping;

import ecommerce.model.Order;

public class ExpressShipping implements ShippingMethod {
    @Override 
    public void ship(Order order) {
        System.out.println("🚀 Expédition EXPRESS de la commande " + order.getId());
    }
    @Override 
    public double fee() { 
        return 9.99; 
    }
    @Override 
    public String name() { 
        return "Express"; 
    }
}
