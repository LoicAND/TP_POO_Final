package ecommerce.model;

public class CartItem {
    private final Product product;
    private final int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product product() { 
        return product; 
    }
    public int quantity() { 
        return quantity; 
    }
}
