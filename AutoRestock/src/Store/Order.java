package Store;

import Events.EventTypes.Customer.ShippingStatus;

import java.time.LocalDateTime;

public class Order {
    private final String orderId;
    private final Person customer;
    private final Product product;
    private final int quantity;
    private final LocalDateTime orderDate;
    private boolean paid;
    private boolean shipped;
    private ShippingStatus shippingStatus;

    public Order(Person customer, Product product, int quantity) {
        this.orderId = generateOrderId();
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.orderDate = LocalDateTime.now();
        this.paid = false;
        this.shipped = false;
        this.shippingStatus = ShippingStatus.AWAITING_PAYMENT;
    }

    private String generateOrderId() {
        return "ORD-" + System.currentTimeMillis() + "-" + (int)(Math.random() * 1000);
    }

    public String getOrderId() { return orderId; }
    public Person getCustomer() { return customer; }
    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public boolean isPaid() { return paid; }
    public boolean isShipped() { return shipped; }
    public ShippingStatus getShippingStatus() { return shippingStatus; }

    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    public void markAsPaid() {
        this.paid = true;
        this.shippingStatus = ShippingStatus.AWAITING_SHIPMENT;
    }

    public void updateShippingStatus(ShippingStatus newStatus) {
        this.shippingStatus = newStatus;
        if (newStatus == ShippingStatus.DELIVERY_CONFIRMED) {
            this.shipped = true;
        }
    }

    public void markAsShipped() {
        this.shippingStatus = ShippingStatus.SHIPPED_BY_SEA;
    }

    @Override
    public String toString() {
        return String.format("Order #%s - %s: %dx %s (€%.2f) - Status: %s",
                orderId,
                customer.getFullName(),
                quantity,
                product.getName(),
                getTotalPrice(),
                shippingStatus.toString().replace("_", " ").toLowerCase());
    }
}