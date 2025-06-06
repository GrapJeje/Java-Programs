package Listeners.Listeners;

import Events.Events.RandomEvent;
import Listeners.Listen;
import Listeners.Listener;

public class RandomListener implements Listener {
    @Listen
    public void onRandomEvent(RandomEvent e) {
        System.out.println("[RANDOM EVENT] " + e.getType() + " occurred with cost: €" +
                String.format("%.2f", e.getCost()));
        System.out.println("  New balance: €" + String.format("%.2f", e.getStore().getMoney()));
    }
}
