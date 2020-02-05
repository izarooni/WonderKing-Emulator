package com.izarooni.wkem.life;

import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.util.Vector2D;
import com.izarooni.wkem.util.Disposable;

/**
 * @author izarooni
 */
public abstract class Entity implements Disposable {

    private int objectID;
    private int hp, mp;
    private int maxHp, maxMp;
    private Vector2D location;

    public abstract void encode(EndianWriter w);

    public final int getObjectID() {
        return objectID;
    }

    public final void setObjectID(int objectID) {
        this.objectID = objectID;
    }

    public final int getHp() {
        return hp;
    }

    public final void setHp(int hp) {
        this.hp = hp;
    }

    public final int getMp() {
        return mp;
    }

    public final void setMp(int mp) {
        this.mp = mp;
    }

    public final int getMaxHp() {
        return maxHp;
    }

    public final void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public final int getMaxMp() {
        return maxMp;
    }

    public final void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
    }

    public final Vector2D getLocation() {
        return location;
    }

    public final void setLocation(Vector2D location) {
        this.location = location;
    }
}
