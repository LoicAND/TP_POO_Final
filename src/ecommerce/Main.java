package ecommerce;

import ecommerce.repository.InMemoryOrderRepository;
import ecommerce.repository.OrderRepository;
import ecommerce.service.CheckoutService;
import ecommerce.service.OrderService;
import ecommerce.UI.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        OrderRepository repo = new InMemoryOrderRepository();
        OrderService orderService = new OrderService(repo);
        CheckoutService checkoutService = new CheckoutService(orderService);

        new ConsoleUI(orderService, checkoutService, repo).run();
    }
}
