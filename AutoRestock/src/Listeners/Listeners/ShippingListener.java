package Listeners.Listeners;

import Events.EventTypes.Customer.ShippingStatus;
import Events.Events.ShippingEvent;
import Listeners.Listen;
import Listeners.Listener;

public class ShippingListener implements Listener {

    @Listen
    public void onShippingEvent(ShippingEvent e) {
        ShippingStatus status = (ShippingStatus) e.getType();
        System.out.println("[SHIPPING] Status update: " + status.toString().toLowerCase().replace("_", " "));
    }
}
