package Listeners.Listeners;

import Events.Events.InventoryEvent;
import Events.EventTypes.Store.Inventory;
import Listeners.Listen;
import Listeners.Listener;

public class InventoryListener implements Listener {

    @Listen
    public void onInventoryEvent(InventoryEvent e) {
        String message;
        Inventory type = (Inventory) e.getType();

        message = switch (type) {
            case STOCK_INCREASED -> "Stock increased by " + e.getCount();
            case STOCK_DECREASED -> "Stock decreased by " + e.getCount();
            case STOCK_LOW -> "Warning: Stock is low after decrease of " + e.getCount();
            case STOCK_OUT -> "Warning: Product is out of stock after decrease of " + e.getCount();
        };

        System.out.println("[INVENTORY] " + message);
    }
}