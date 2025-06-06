package Events.Events;

import Events.Event;
import Events.EventTypes.Customer.ShippingStatus;

public class ShippingEvent extends Event {

    public ShippingEvent(ShippingStatus status) {
        super(status);
    }
}
