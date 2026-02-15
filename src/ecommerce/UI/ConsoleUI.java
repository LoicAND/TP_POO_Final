package ecommerce.UI;

import ecommerce.factory.ProductFactory;
import ecommerce.model.Customer;
import ecommerce.model.Order;
import ecommerce.payment.CreditCardPayment;
import ecommerce.payment.PaypalPayment;
import ecommerce.payment.PaymentMethod;
import ecommerce.repository.OrderRepository;
import ecommerce.service.CheckoutService;
import ecommerce.service.OrderService;
import ecommerce.shipping.ExpressShipping;
import ecommerce.shipping.ShippingMethod;
import ecommerce.shipping.StandardShipping;

import java.util.Scanner;

public class ConsoleUI {
    private final OrderService orderService;
    private final CheckoutService checkoutService;
    private final OrderRepository repo;
    private ShippingMethod savedShipping;

    public ConsoleUI(OrderService orderService, CheckoutService checkoutService, OrderRepository repo) {
        this.orderService = orderService;
        this.checkoutService = checkoutService;
        this.repo = repo;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);

        Customer customer = new Customer("C1", "Alice", "alice@mail.com");
        var tshirt = ProductFactory.createPhysical("P1", "T-shirt", 19.99, 0.3);
        var ebook = ProductFactory.createDigital("P2", "E-book Java", 9.99, "http://download/ebook");

        Order order = new Order("O1", customer);
        order.addItem(tshirt, 1);
        order.addItem(ebook, 1);
        orderService.create(order);

        while (true) {
            System.out.println("\n=== E-COMMERCE (DEMO SIMPLE) ===");
            System.out.println("1. Afficher commande");
            System.out.println("2. Payer");
            System.out.println("3. Expédier");
            System.out.println("4. Voir historique");
            System.out.println("0. Quitter");
            System.out.print("Choix: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1": 
                    showOrder(order);  
                    break;
                case "2":  
                    payFlow(sc, order); 
                    break;
                case "3":  
                    shipFlow(sc, order); 
                    break;
                case "4": 
                    showHistory(); 
                    break;
                case "0": 
                    System.out.println("Bye."); return;
                default:    
                    System.out.println("Choix invalide."); break;
            }
        }
    }

    private void showOrder(Order order) {
        System.out.println("\nCommande " + order.getId() + " - Status: " + order.getStatus());
        order.getItems().forEach(i ->
                System.out.println("- " + i.product().getName() + " x" + i.quantity() + " = " +
                        (i.product().getPrice() * i.quantity()) + " €")
        );
        double itemsTotal = orderService.computeItemsTotal(order);
        System.out.println("Total articles: " + itemsTotal + " €");
    }

    private void payFlow(Scanner sc, Order order) {
        PaymentMethod method = pickPayment(sc);
        ShippingMethod shipping = pickShipping(sc);
        savedShipping = shipping;

        double total = checkoutService.totalWithShipping(order, shipping);
        System.out.println("Total (articles + livraison " + shipping.name() + "): " + total + " €");
        checkoutService.pay(order, method, total);
        System.out.println("Status => " + order.getStatus());
    }

    private void shipFlow(Scanner sc, Order order) {
        if (savedShipping == null) {
            System.out.println("Veuillez d'abord payer la commande.");
            return;
        }
        checkoutService.ship(order, savedShipping);
        System.out.println("Status => " + order.getStatus());
    }

    private PaymentMethod pickPayment(Scanner sc) {
        System.out.println("\nPaiement: 1) CB  2) PayPal");
        System.out.print("Choix: ");
        String c = sc.nextLine();
        return c.equals("2") ? new PaypalPayment() : new CreditCardPayment();
    }

    private ShippingMethod pickShipping(Scanner sc) {
        System.out.println("\nLivraison: 1) Standard  2) Express");
        System.out.print("Choix: ");
        String c = sc.nextLine();
        return c.equals("2") ? new ExpressShipping() : new StandardShipping();
    }

    private void showHistory() {
        System.out.println("\n=== Historique commandes ===");
        repo.findAll().forEach(o ->
                System.out.println("- " + o.getId() + " (" + o.getStatus() + ") client=" + o.getCustomer().getName())
        );
    }
}
