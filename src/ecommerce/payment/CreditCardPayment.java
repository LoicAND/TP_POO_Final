public class CreditCardPayment implements PaymentMethod {
    @Override public void pay(double amount) {
        System.out.println("✅ Paiement CB effectué : " + amount + " €");
    }
    @Override public String name() { 
        return "CB"; 
    }
}
