package ecommerce.factory;

import ecommerce.model.DigitalProduct;
import ecommerce.model.PhysicalProduct;
import ecommerce.model.Product;

public class ProductFactory {

    public static Product createPhysical(String id, String name, double price, double weightKg) {
        return new PhysicalProduct(id, name, price, weightKg);
    }

    public static Product createDigital(String id, String name, double price, String url) {
        return new DigitalProduct(id, name, price, url);
    }
}
