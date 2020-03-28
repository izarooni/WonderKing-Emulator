package com.izarooni.wkem.life.meta.storage;

import com.izarooni.wkem.packet.accessor.EndianWriter;

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

    private final StorageType type;
    private final HashMap<Byte, Item> items;
    private short capacity;
    private byte bags;

    public Storage(StorageType type, short capacity, byte bags) {
        this.type = type;
        this.capacity = capacity;
        this.bags = bags;
        items = new HashMap<>(20);
    }

    public void encodeItemIDs(EndianWriter w, int count) {
        for (byte i = 0; i < count; i++) {
            Item item = items.get(i);
            if (item != null) w.writeShort(item.getId());
            else w.writeShort(0);
        }
    }

    public void encodeItemStats(EndianWriter w, int count) {
        encodeItemIDs(w, count);
        for (int i = 0; i < count; i++) {
            /*
            w.write(itemLevel);
            w.write(itemRarity);
            w.write(itemAddOption);
            w.write(itemAddOption2);
            w.write(itemAddOption3);
            w.writeShort(itemOption);
            w.writeShort(itemOption2);
            w.writeShort(itemOption3);
             */
            w.skip(11);
        }
    }

    public void encodeInventory(EndianWriter w, int bags) {
        w.write(bags); // eqp_bags
        w.write(bags); // etc_bags

        for (byte bag = 0; bag < bags; bag++) {
            final byte start = (byte) (20 * bag), end = (byte) (20 * (bag + 1));
            for (byte key = start; key < end; key++) {
                Item item = items.get(key);
                if (item != null) w.writeShort(item.getId());
                else w.writeShort(0);
            }
            for (byte key = start; key < end; key++) {
                Item item = items.get(key);
                if (item != null) w.writeShort(item.getQuantity());
                else w.writeShort(0);
            }
            for (short key = start; key < end; key++) {
                /*
                w.write(itemLevel);
                w.write(itemRarity);
                w.write(itemAddOption);
                w.write(itemAddOption2);
                w.write(itemAddOption3);
                w.writeShort(itemOption);
                w.writeShort(itemOption2);
                w.writeShort(itemOption3);
                 */
                w.skip(11);
            }
        }
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
        item.setInventorySlot(dst);
        return items.put(dst, item);
    }

    public Item equipItem(Item item) {
        if (items.size() >= capacity) {
            throw new IndexOutOfBoundsException(String.format("Can't put more than %d items", capacity));
        }
        return items.put((byte) item.getTemplate().slotNo, item);
    }

    public Item removeItem(Item item) {
        if (type == StorageType.Equipped) {
            return items.remove((byte) item.getTemplate().slotNo);
        } else {
            return items.remove(item.getInventorySlot());
        }
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
