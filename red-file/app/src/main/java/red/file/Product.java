package red.file;

public class Product {
    private int id;
    private String name;
    private float price;
    private int Quantity;

    public Product(int id, String name, float price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }
    
    public int getQuantity() {
        return Quantity;
    }
}
