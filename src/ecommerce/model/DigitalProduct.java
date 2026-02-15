public class DigitalProduct extends Product {
    private final String downloadUrl;

    public DigitalProduct(String id, String name, double price, String downloadUrl) {
        super(id, name, price);
        this.downloadUrl = downloadUrl;
    }

    public String getDownloadUrl() { 
        return downloadUrl; 
    }

    @Override public String getType() { 
        return "DIGITAL"; 
    }
}
