package Store;

import Events.EventTypes.Customer.*;
import Events.EventTypes.Store.*;
import Events.Events.*;

import java.util.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiPredicate;

public class Store {
    private final String name;
    private final Map<Product, Integer> stock;
    private double money;
    private final List<Order> pendingOrders;

    public Store(String name) {
        this.name = name;
        this.stock = new HashMap<>();
        this.money = Math.round(ThreadLocalRandom.current().nextDouble(250.00, 5000.00) * 100.0) / 100.0;
        this.pendingOrders = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public List<Order> getPendingOrders() {
        return new ArrayList<>(pendingOrders);
    }

    public Store addOrder(Order order) {
        pendingOrders.add(order);
        return this;
    }

    public Store addProduct(Product product, int quantity) {
        if (product == null || quantity <= 0) return this;
        stock.put(product, stock.getOrDefault(product, 0) + quantity);
        return this;
    }

    public Store addProducts(Map<Product, Integer> products) {
        if (products == null) return this;
        products.forEach(this::addProduct);
        return this;
    }

    public Store updateStock(Product product, int newQuantity) {
        if (product == null || newQuantity < 0) return this;
        stock.put(product, newQuantity);
        return this;
    }

    public Store updateStock(Map<Product, Integer> newStock) {
        if (newStock == null) return this;
        newStock.forEach(this::updateStock);
        return this;
    }

    public Store removeProduct(Product product) {
        if (product != null) stock.remove(product);
        return this;
    }

    public Store clearStock() {
        stock.clear();
        return this;
    }

    public Store filterStock(BiPredicate<Product, Integer> condition) {
        stock.entrySet().removeIf(entry -> !condition.test(entry.getKey(), entry.getValue()));
        return this;
    }

    public int getStock(Product product) {
        return stock.getOrDefault(product, 0);
    }

    public Map<Product, Integer> getAllStock() {
        return new HashMap<>(stock);
    }

    public Store generateRandomProducts(int count) {
        String[] baseNames = {
                "Appel", "Banaan", "Chips", "Kaas", "Brood", "Melk", "Cola", "Pizza",
                "Pasta", "Saus", "Koffie", "Thee", "Yoghurt", "Snoep", "Water", "Wijn",
                "Bier", "Zeep", "Shampoo", "Toiletpapier"
        };
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            String name = baseNames[random.nextInt(baseNames.length)];
            double rawPrice = 1.00 + (20.00 - 1.00) * random.nextDouble();
            double price = Math.round(rawPrice * 100.0) / 100.0;
            Product product = new Product(name, price);
            if (stock.containsKey(product)) {
                count--;
                continue;
            }
            int quantity = random.nextInt(100) + 1;
            stock.put(product, quantity);
        }

        return this;
    }

    public void open() {
        System.out.println("Store " + name + " is now open!");
        System.out.println("Initial money: €" + String.format("%.2f", money));
        System.out.println("Initial stock:");
        stock.forEach((product, quantity) ->
                System.out.println("- " + product.getName() + ": " + quantity + " (€" +
                        String.format("%.2f", product.getPrice()) + " each)"));

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (money <= 0) {
                    System.out.println("⚠️ Store is bankrupt with €" +
                            String.format("%.2f", money) + "! Closing store...");
                    timer.cancel();
                    return;
                }
                System.out.println("\n=== Store Status ===");
                System.out.println("Balance: €" + String.format("%.2f", money));
                System.out.println("Pending orders: " + pendingOrders.size());
                this.processPaymentsAndShipping();
                int eventType = ThreadLocalRandom.current().nextInt(0, 4);
                switch (eventType) {
                    case 0:
                        if (!stock.isEmpty() && !Person.people.isEmpty())
                            Person.randomOrder(Person.people, Store.this);
                        break;
                    case 1:
                        if (!stock.isEmpty()) {
                            Product randomProduct = new ArrayList<>(stock.keySet())
                                    .get(ThreadLocalRandom.current().nextInt(stock.size()));
                            int change = ThreadLocalRandom.current().nextInt(1, 11);
                            boolean increase = ThreadLocalRandom.current().nextBoolean();

                            if (increase) {
                                stock.put(randomProduct, stock.get(randomProduct) + change);
                                new InventoryEvent(Inventory.STOCK_INCREASED, change);
                            } else {
                                int currentStock = stock.get(randomProduct);
                                int newStock = Math.max(0, currentStock - change);
                                stock.put(randomProduct, newStock);

                                if (newStock == 0) new InventoryEvent(Inventory.STOCK_OUT, change);
                                else if (newStock < 5) new InventoryEvent(Inventory.STOCK_LOW, change);
                                else new InventoryEvent(Inventory.STOCK_DECREASED, change);
                            }
                        }
                        break;
                    case 2:
                        new RandomEvent(Store.this);
                        break;
                    case 3:
                        ShippingStatus[] statuses = ShippingStatus.values();
                        ShippingStatus randomStatus = statuses[ThreadLocalRandom.current().nextInt(statuses.length)];
                        new ShippingEvent(randomStatus);
                        break;
                }
                if (money <= 0) {
                    System.out.println("⚠️ Store is bankrupt with €" +
                            String.format("%.2f", money) + "! Closing store...");
                    timer.cancel();
                }
            }

            private void processPaymentsAndShipping() {
                List<Order> toPay = pendingOrders.stream()
                        .filter(order -> !order.isPaid())
                        .toList();
                if (!toPay.isEmpty()) {
                    System.out.println("\nProcessing payments for " + toPay.size() + " orders...");
                    for (Order order : toPay) {
                        double totalPrice = order.getTotalPrice();
                        money += totalPrice;
                        order.markAsPaid();
                        System.out.println("+ €" + String.format("%.2f", totalPrice) +
                                " from " + order.getCustomer().getFullName() +
                                " for " + order.getQuantity() + "x " + order.getProduct().getName());
                    }
                }
                List<Order> toShip = pendingOrders.stream()
                        .filter(order -> order.isPaid() && !order.isShipped())
                        .toList();
                if (!toShip.isEmpty()) {
                    System.out.println("\nShipping " + toShip.size() + " orders...");
                    for (Order order : toShip) {
                        Product product = order.getProduct();
                        int currentStock = stock.getOrDefault(product, 0);
                        stock.put(product, currentStock - order.getQuantity());
                        order.markAsShipped();
                        System.out.println("- Shipped " + order.getQuantity() + "x " +
                                product.getName() + " to " + order.getCustomer().getFullName());
                        ShippingStatus[] statuses = ShippingStatus.values();
                        ShippingStatus randomStatus = statuses[ThreadLocalRandom.current().nextInt(statuses.length)];
                        new ShippingEvent(randomStatus);
                    }
                    pendingOrders.removeIf(Order::isShipped);
                }
            }
        }, 0, 10000);
    }
}
