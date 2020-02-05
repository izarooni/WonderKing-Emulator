package com.izarooni.wkem.life.meta;

/**
 * @author izarooni
 */
public enum Element {
    Fire, Water, Dark, Holy;
    private Element weakness;

    static {
        Fire.weakness = Water;
        Water.weakness = Fire;
        Dark.weakness = Holy;
        Holy.weakness = Dark;
    }

    public Element getWeakness() {
        return weakness;
    }
}
