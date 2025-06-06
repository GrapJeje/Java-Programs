package Listeners.Listeners;

import Events.Events.OrderEvent;
import Listeners.Listen;
import Listeners.Listener;

public class OrderListener implements Listener {
    @Listen
    public void onOrderEvent(OrderEvent e) {
        System.out.println("[NEW ORDER] " + e.getCustomer().getFullName() +
                " ordered " + e.getAmount() + "x " + e.getProduct().getName() +
                " (Total: €" + String.format("%.2f", e.getPrice()) + ")");
    }
}