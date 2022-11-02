package me.heroostech.citystom.listener;

import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;

public interface Listener<E extends Event> {
    EventNode<E> events();
}
