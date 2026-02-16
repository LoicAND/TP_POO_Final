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
import ecommerce.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    private List<Product> buildCatalog() {
        List<Product> catalog = new ArrayList<>();
        catalog.add(ProductFactory.createPhysical("P1",  "T-shirt",        19.99,  0.3));
        catalog.add(ProductFactory.createPhysical("P2",  "Jean slim",      39.99,  0.8));
        catalog.add(ProductFactory.createPhysical("P3",  "Baskets",        89.99,  1.2));
        catalog.add(ProductFactory.createPhysical("P4",  "Casquette",      14.99,  0.2));
        catalog.add(ProductFactory.createPhysical("P5",  "Sac à dos",      49.99,  0.9));
        catalog.add(ProductFactory.createDigital ("P6",  "E-book Java",     9.99, "https://dl.shop/ebook-java"));
        catalog.add(ProductFactory.createDigital ("P7",  "Cours UML",      14.99, "https://dl.shop/cours-uml"));
        catalog.add(ProductFactory.createDigital ("P8",  "Album MP3",       7.99, "https://dl.shop/album-mp3"));
        catalog.add(ProductFactory.createDigital ("P9",  "Pack icônes",     4.99, "https://dl.shop/pack-icons"));
        catalog.add(ProductFactory.createPhysical("P10", "Clavier mécanique", 59.99, 0.7));
        return catalog;
    }

    private List<Customer> buildCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("C1", "Alice",   "alice@mail.com"));
        customers.add(new Customer("C2", "Bob",     "bob@mail.com"));
        customers.add(new Customer("C3", "Charlie", "charlie@mail.com"));
        customers.add(new Customer("C4", "Diana",   "diana@mail.com"));
        customers.add(new Customer("C5", "Eve",     "eve@mail.com"));
        return customers;
    }

    private Order generateRandomOrder(List<Product> catalog, List<Customer> customers, Random rnd, int orderNum) {
        Customer customer = customers.get(rnd.nextInt(customers.size()));
        Order order = new Order("O" + orderNum, customer);

        int nbItems = 1 + rnd.nextInt(4);         
        for (int i = 0; i < nbItems; i++) {
            Product product = catalog.get(rnd.nextInt(catalog.size()));
            int qty = 1 + rnd.nextInt(3);           
            order.addItem(product, qty);
        }
        orderService.create(order);
        return order;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        Random rnd = new Random();

        List<Product> catalog   = buildCatalog();
        List<Customer> customers = buildCustomers();
        int orderNum = 0;
        Order order = null;

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
                    orderNum++;
                    order = generateRandomOrder(catalog, customers, rnd, orderNum);
                    savedShipping = null;
                    System.out.println("\n✅ Commande pour " + order.getCustomer().getName() + " !");
                    showOrder(order);
                    break;
                case "2":
                    if (order == null) { System.out.println("Veuillez d'abord afficher une commande."); break; }
                    payFlow(sc, order); 
                    break;
                case "3":
                    if (order == null) { System.out.println("Veuillez d'abord afficher une commande."); break; }
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
