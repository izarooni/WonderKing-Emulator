package com.izarooni.wkem.life.meta;

import com.izarooni.wkem.packet.accessor.EndianWriter;

/**
 * @author izarooni
 */
public class Elements {

    public static class Attributes {
        private int[] elements;

        private Attributes() {
            Element[] values = Element.values();
            elements = new int[values.length];
        }

        public int getFire() {
            return elements[Element.Fire.ordinal()];
        }

        public void setFire(int v) {
            elements[Element.Fire.ordinal()] = v;
        }

        public int getWater() {
            return elements[Element.Water.ordinal()];
        }

        public void setWater(int v) {
            elements[Element.Water.ordinal()] = v;
        }

        public int getDark() {
            return elements[Element.Dark.ordinal()];
        }

        public void setDark(int v) {
            elements[Element.Dark.ordinal()] = v;
        }

        public int getHoly() {
            return elements[Element.Holy.ordinal()];
        }

        public void setHoly(int v) {
            elements[Element.Holy.ordinal()] = v;
        }
    }

    private Attributes damage;
    private Attributes resistance;

    public Elements() {
        damage = new Attributes();
        resistance = new Attributes();
    }

    public void encode(EndianWriter w) {
        w.writeShort(damage.getFire());
        w.writeShort(damage.getWater());
        w.writeShort(damage.getDark());
        w.writeShort(damage.getHoly());
        w.writeShort(resistance.getFire());
        w.writeShort(resistance.getWater());
        w.writeShort(resistance.getDark());
        w.writeShort(resistance.getHoly());
    }

    public Attributes getDamage() {
        return damage;
    }

    public Attributes getResistance() {
        return resistance;
    }
}
