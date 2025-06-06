package Events;

import Listeners.Listen;
import Listeners.Listener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class Event {
    private static final List<Listener> listeners = new ArrayList<>();
    private final Enum<?> type;

    public Event(Enum<?> type) {
        this.type = type;
        callEvent(this);
    }

    public Enum<?> getType() {
        return type;
    }

    public static void registerListener(Listener listener) {
        listeners.add(listener);
    }

    private static void callEvent(Event event) {
        for (Listener listener : listeners) {
            for (Method method : listener.getClass().getDeclaredMethods()) {
                if (!method.isAnnotationPresent(Listen.class)) continue;
                if (method.getParameterCount() != 1) continue;
                Class<?> paramType = method.getParameterTypes()[0];
                if (!paramType.isAssignableFrom(event.getClass())) continue;
                try {
                    method.setAccessible(true);
                    method.invoke(listener, event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
