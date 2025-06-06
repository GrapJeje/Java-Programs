package Events.Events;

import Events.Event;
import Events.EventTypes.Store.Random;
import Store.Store;

public class RandomEvent extends Event {

    private final double cost;
    private final Store store;

    public RandomEvent(Store store) {
        this.store = store;
        Random[] events = Random.values();
        int idx = new java.util.Random().nextInt(events.length);
        Random chosenEvent = events[idx];
        this.cost = chosenEvent.getCost();
        store.setMoney(store.getMoney() - chosenEvent.getCost());
        super(chosenEvent);
    }

    public double getCost() {
        return cost;
    }

    public Store getStore() {
        return store;
    }
}
