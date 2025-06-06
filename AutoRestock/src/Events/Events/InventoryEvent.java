package Events.Events;

import Events.Event;
import Events.EventTypes.Store.Inventory;

public class InventoryEvent extends Event {

    private final int count;

    public InventoryEvent(Inventory inventory, int count) {
        this.count = count;
        super(inventory);
    }

    public int getCount() {
        return count;
    }
}
