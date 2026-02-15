package ecommerce.payment;

public class PaypalPayment implements PaymentMethod {
    @Override public void pay(double amount) {
        System.out.println("✅ Paiement PayPal effectué : " + amount + " €");
    }
    @Override public String name() { 
        return "PayPal"; 
    }
}

