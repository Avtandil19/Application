import com.sun.security.jgss.GSSUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static Map<String, Product> products;
    private static boolean exit = false;

    public static void main(String[] args) {
        products = new HashMap<>();
        do {
            inputData();
        } while (!exit);
    }


    /**
     * Allows user to enter data
     * and stores input parts in string array.
     * Checks command and acts appropriately
     */
    private static void inputData() {
        Scanner input = new Scanner(System.in);
        String[] lineParts = getLinePieces(input);
        checkCommand(lineParts);
    }


    /**
     * Takes user's input and add its parts into string array
     * @param input user's input string
     * @return string array of user's input line parts
     */
    private static String[] getLinePieces(Scanner input) {
        String line = input.nextLine();
        return line.split(" ");
    }


    /**
     * Checks command and acts appropriately
     * @param lineParts string array of user's input line parts
     */
    private static void checkCommand(String[] lineParts) {
        String command = lineParts[0];
        if (Objects.equals(command, "save_product")) {
            saveProduct(lineParts);
        } else if (Objects.equals(command, "purchase_product")) {
            purchaseProduct(lineParts);
        } else if (Objects.equals(command, "order_product")) {
            orderProduct(lineParts);
        } else if (Objects.equals(command, "get_quantity_of_product")) {
            getQuantityOfProduct(lineParts);
        } else if (Objects.equals(command, "get_average_price")) {
            getAveragePrice(lineParts);
        } else if (Objects.equals(command, "get_product_profit")) {
            getProductProfit(lineParts);
        } else if (Objects.equals(command, "get_fewest_product")) {
            getFewestProduct(lineParts);
        } else if (Objects.equals(command, "get_most_popular_product")) {
            getMostPopularProduct(lineParts);
        } else if (Objects.equals(command, "exit")) {
            exitApplication();
        } else {
            System.out.println("Wrong Command!");
        }
    }


    /**
     * Exits the application
     */
    private static void exitApplication() {
        exit = true;
    }


    /**
     * Prints the name of the product with the highest number of orders
     * @param lineParts string array of user's input line parts
     */
    private static void getMostPopularProduct(String[] lineParts) {
        int nOrders = 0;
        String name = "";

        for (String id: products.keySet()) {
            Product product = products.get(id);
            int productOrders = product.numberOfOrders();
            if (productOrders > nOrders) {
                nOrders = productOrders;
                name = product.getName();
            }
        }

        System.out.println(name);
    }


    /**
     * Prints the name of the product with the lowest remaining quantity
     * @param lineParts string array of user's input line parts
     */
    private static void getFewestProduct(String[] lineParts) {
        String name = "";
        int quantity = Integer.MAX_VALUE;

        for (String id: products.keySet()) {
            Product product = products.get(id);
            int productQuantity = product.getQuantity();
            if (productQuantity < quantity) {
                quantity = productQuantity;
                name = product.getName();
            }
        }

        System.out.println(name);
    }


    /**
     * Calculates and displays the profit earned from a specific product
     * by comparing the average purchase price with the average order price
     * @param lineParts string array of user's input line parts
     */
    private static void getProductProfit(String[] lineParts) {
        String productId = lineParts[1];
        Product product = products.get(productId);
        if (product != null) {
            double productProfit = product.getProfit();
            System.out.println("Product Profit: " + productProfit);
        } else {
            System.out.println("There is no product with this id");
        }
    }


    /**
     * Calculates and displays the average price of a specific product
     * based on its purchase history
     * @param lineParts string array of user's input line parts
     */
    private static void getAveragePrice(String[] lineParts) {
        String productId = lineParts[1];
        Product product = products.get(productId);
        if (product != null) {
            double avgPrice = product.averagePurchasePrice();
            System.out.println("Average Price: " + avgPrice);
        } else {
            System.out.println("There is no product with this id");
        }
    }


    /**
     * Returns the remaining quantity of a specific product
     * @param lineParts string array of user's input line parts
     */
    private static void getQuantityOfProduct(String[] lineParts) {
        String productId = lineParts[1];
        Product product = products.get(productId);
        if (product != null) {
            System.out.println("Remaining quantity: " + product.getQuantity());
        } else {
            System.out.println("There is no product with this id");
        }
    }


    /**
     * Places an order for the product
     * @param lineParts string array of user's input line parts
     */
    private static void orderProduct(String[] lineParts) {
        String productId = lineParts[1];
        int productQuantity = Integer.parseInt(lineParts[2]);
        Product product = products.get(productId);
        if (product != null) {
            product.orderProduct(productQuantity);
        } else {
            System.out.println("There is no product with this id");
        }
    }


    /**
     * Purchases a product
     * @param lineParts string array of user's input line parts
     */
    private static void purchaseProduct(String[] lineParts) {
        String productId = lineParts[1];
        int productQuantity = Integer.parseInt(lineParts[2]);
        double productPrice = Double.parseDouble(lineParts[3]);
        Product product = products.get(productId);
        if (product != null) {
            product.purchaseProduct(productQuantity, productPrice);
        } else {
            System.out.println("There is no product with this id");
        }
    }


    /**
     * Adds a new product to the catalog or modifies
     * an existing one.
     * @param lineParts string array of user's input line parts
     */
    private static void saveProduct(String[] lineParts) {
        String productId = lineParts[1];
        String productName = lineParts[2];
        double sellingPrice = Double.parseDouble(lineParts[3]);
        if (products.containsKey(productId)) {
            Product product = products.get(productId);
            product.setPrice(sellingPrice);
        } else {
            Product newProduct = new Product(productId, productName);
            newProduct.setPrice(sellingPrice);
            products.put(productId, newProduct);
        }
    }
}