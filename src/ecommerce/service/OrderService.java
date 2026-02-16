package ecommerce.service;

import ecommerce.model.Order;
import ecommerce.model.OrderStatus;
import ecommerce.repository.OrderRepository;

public class OrderService {
    private final OrderRepository repo;

    public OrderService(OrderRepository repo) {
        this.repo = repo;
    }

    public void create(Order order) {
        repo.save(order);
    }

    public double computeItemsTotal(Order order) {
        return order.getItems().stream()
                .mapToDouble(i -> i.product().getPrice() * i.quantity())
                .sum();
    }

    public void markPaid(Order order) {
        order.setStatus(OrderStatus.PAID);
        repo.save(order);
    }

    public void markShipped(Order order) {
        order.setStatus(OrderStatus.SHIPPED);
        repo.save(order);
    }
}
