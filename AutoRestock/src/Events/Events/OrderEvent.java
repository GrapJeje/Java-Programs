package Events.Events;

import Events.Event;
import Events.EventTypes.Customer.Order;
import Store.Person;
import Store.Product;
import Store.Store;

public class OrderEvent extends Event {
    private final Product product;
    private final int amount;
    private final Store store;
    private final Person customer;

    public OrderEvent(Order order, Product product, int amount, Store store, Person customer) {
        this.product = product;
        this.amount = amount;
        this.store = store;
        this.customer = customer;
        double totalPrice = product.getPrice() * amount;
        store.setMoney(store.getMoney() + totalPrice);
        super(order);
    }

    public Product getProduct() {
        return product;
    }

    public int getAmount() {
        return amount;
    }

    public double getPrice() {
        return product.getPrice() * amount;
    }

    public Person getCustomer() {
        return customer;
    }

    public Store getStore() {
        return store;
    }
}
