import Events.Event;
import Listeners.Listeners.*;
import Store.*;

public static void main(String[] args) {
    register();

    Person.generatePeople(30);
    Store store = new Store("GrapJeje B.V").generateRandomProducts(10);
    store.open();
}

public static void register() {
    Event.registerListener(new OrderListener());
    Event.registerListener(new InventoryListener());
    Event.registerListener(new RandomListener());
    Event.registerListener(new ShippingListener());
}