package Events.EventTypes.Store;

import java.util.*;

public enum Random {
    EQUIPMENT_REPAIR(120.50, 150.75, 180.00),
    ADVERTISEMENT(250.00, 300.50, 350.75),
    STAFF_TRAINING(150.00, 200.00, 250.00),
    CLEANING_SERVICE(80.00, 100.00, 120.00),
    UTILITY_BILL(200.00, 250.25, 300.00),
    SOFTWARE_UPDATE(160.00, 180.00, 200.00);

    private final List<Double> cost;

    Random(Double... cost) {
        this.cost = new ArrayList<>(Arrays.asList(cost));
    }

    public double getCost() {
        return cost.get(new java.util.Random().nextInt(cost.size()));
    }
}
