package com.izarooni.wkem.life.meta.storage;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author izarooni
 */
public class Storage {

    private final HashMap<Byte, Item> items;
    private short capacity;
    private byte bags;

    public Storage(short capacity, byte bags) {
        this.capacity = capacity;
        this.bags = bags;
        items = new HashMap<>(20);
    }

    public byte getFirstAvailableSlot() {
        for (byte i = 0; i < capacity; i++) {
            if (!items.containsKey(i)) return i;
        }
        return -1;
    }

    public Collection<Item> getItems() {
        return Collections.unmodifiableCollection(items.values());
    }

    public void forEach(Consumer<Item> c) {
        items.values().forEach(c);
    }

    public Stream<Item> findItem(Predicate<Item> p) {
        return items.values().stream().filter(p);
    }

    /**
     * @param dst  destination slot in the Map
     * @param item item object to store
     * @return previous item in the specified index if one exists
     */
    public Item putItem(byte dst, Item item) {
        if (items.size() >= capacity) {
            throw new IndexOutOfBoundsException(String.format("Can't put more than %d items", capacity));
        }
        return items.put(dst, item);
    }

    public Item putItem(Item item) {
        if (items.size() >= capacity && !items.containsKey((byte) item.getTemplate().slotNo)) {
            throw new IndexOutOfBoundsException(String.format("Can't put more than %d items", capacity));
        }
        return items.put((byte) item.getTemplate().slotNo, item);
    }

    public short getCapacity() {
        return capacity;
    }

    public void setCapacity(short capacity) {
        this.capacity = capacity;
    }

    public byte getBags() {
        return bags;
    }

    public void setBags(byte bags) {
        this.bags = bags;
    }
}
