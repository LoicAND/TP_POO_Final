package ecommerce.repository;

import ecommerce.model.Order;

import java.util.*;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<String, Order> store = new HashMap<>();

    @Override 
    public void save(Order order) { 
        store.put(order.getId(), order); 
    }

    @Override 
    public Optional<Order> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override 
    public List<Order> findAll() {
        return new ArrayList<>(store.values());
    }
}
