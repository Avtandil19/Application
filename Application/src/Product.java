import java.util.HashMap;
import java.util.Map;

public class Product {
    private String id;
    private String name;
    private double sellingPrice;
    private int quantity;
    private double income;
    private double expenses;
    private int sold;
    private Map<Double, Integer> purchaseHistory;
    private Map<Double, Integer> orderHistory;


    /**
     * Constructor of product class.
     * Initializes instance variables
     * @param id product id
     * @param name product name
     */
    public Product(String id, String name) {
        this.id = id;
        this.name = name;
        quantity = 0;
        sold = 0;
        expenses = 0.0;
        income = 0.0;
        sellingPrice = 0.0;
        purchaseHistory = new HashMap<>();
        orderHistory = new HashMap<>();
    }


    /**
     * Sets price
     * @param sellingPrice product new price
     */
    public void setPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }


    /**
     * Purchases new product
     * @param quantity quantity of product
     * @param price price of one product
     */
    public void purchaseProduct(int quantity, double price) {
        this.quantity += quantity;
        expenses += quantity * price;
        if (purchaseHistory.containsKey(price)) {
            int newQuantity = purchaseHistory.get(price) + quantity;
            purchaseHistory.put(price, newQuantity);
        } else {
            purchaseHistory.put(price, quantity);
        }
    }


    /**
     * Orders products
     * @param productQuantity quantity of product
     */
    public void orderProduct(int productQuantity) {
        if (quantity >= productQuantity) {
            quantity -= productQuantity;
            income += productQuantity * sellingPrice;
            sold += productQuantity;
            if (orderHistory.containsKey(sellingPrice)) {
                int newQuantity = orderHistory.get(sellingPrice) + productQuantity;
                orderHistory.put(sellingPrice, newQuantity);
            } else {
                orderHistory.put(sellingPrice, productQuantity);
            }
        } else {
            System.out.println("Not enough quantity!");
        }
    }


    /**
     * @return Returns quantity of product
     */
    public int getQuantity() {
        return quantity;
    }


    /**
     * @return Returns average purchase price
     */
    public double averagePurchasePrice() {
        double totalPrice = 0.0;
        int totalQuantity = 0;

        for (double price: purchaseHistory.keySet()) {
            int productQuantity = purchaseHistory.get(price);
            totalQuantity += productQuantity;
            totalPrice += price * productQuantity;
        }

        return totalPrice / totalQuantity;
    }


    /**
     * @return Returns average order price
     */
    private double averageOrderPrice() {
        double totalPrice = 0.0;
        int totalQuantity = 0;

        for (double price: orderHistory.keySet()) {
            int productQuantity = orderHistory.get(price);
            totalQuantity += productQuantity;
            totalPrice += price * productQuantity;
        }

        return totalPrice / totalQuantity;
    }


    /**
     * @return Returns profit
     */
    public double getProfit() {
        return sold * (averageOrderPrice() - averagePurchasePrice());
    }


    /**
     * @return Returns name of product
     */
    public String getName() {
        return name;
    }


    /**
     * @return Returns number of orders
     */
    public int numberOfOrders() {
        int totalQuantity = 0;

        for (double price: orderHistory.keySet()) {
            totalQuantity += orderHistory.get(price);
        }

        return totalQuantity;
    }
}
