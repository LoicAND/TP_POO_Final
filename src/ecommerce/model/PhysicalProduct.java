package ecommerce.model;

public class PhysicalProduct extends Product {
    private final double weightKg;

    public PhysicalProduct(String id, String name, double price, double weightKg) {
        super(id, name, price);
        this.weightKg = weightKg;
    }

    public double getWeightKg() { 
        return weightKg; 
    }

    @Override 
    public String getType() { 
        return "PHYSICAL"; 
    }
}

