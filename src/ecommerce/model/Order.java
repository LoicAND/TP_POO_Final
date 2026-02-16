package ecommerce.model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String id;
    private final Customer customer;
    private final List<CartItem> items = new ArrayList<>();
    private OrderStatus status = OrderStatus.CREATED;

    public Order(String id, Customer customer) {
        this.id = id;
        this.customer = customer;
    }

    public String getId() { 
        return id; 
    }
    
    public Customer getCustomer() { 
        return customer;    
    }
    
    public List<CartItem> getItems() { 
        return items; 
    }
    
    public OrderStatus getStatus() { 
        return status; 
    }

    public void setStatus(OrderStatus status) { 
        this.status = status; 
    }

    public void addItem(Product product, int qty) {
        if (qty <= 0) throw new IllegalArgumentException("Quantité invalide");
        items.add(new CartItem(product, qty));
    }
}
