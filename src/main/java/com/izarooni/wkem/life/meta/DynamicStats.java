package com.izarooni.wkem.life.meta;

import com.izarooni.wkem.packet.accessor.EndianWriter;

/**
 * @author izarooni
 */
public class DynamicStats {

    private int minWeaponAttack, maxWeaponAttack;
    private int minRangeAttack, maxRangeAttack;
    private int PDD;
    private int hitRate;
    private int evasion;

    private int[] elementDamage;
    private int[] elementResistance;

    public DynamicStats() {
        elementDamage = new int[Element.values().length];
        elementResistance = new int[Element.values().length];
    }

    // 14 bytes
    public void encodeAttack(EndianWriter w) {
        w.writeShort(minWeaponAttack);
        w.writeShort(maxWeaponAttack);
        w.writeShort(minRangeAttack);
        w.writeShort(maxRangeAttack);
        w.writeShort(PDD);
        w.writeShort(hitRate);
        w.writeShort(evasion);
    }

    public void encodeElements(EndianWriter w) {
        Element[] values = Element.values();
        for (Element e : values) {
            w.writeShort(elementDamage[e.ordinal()]);
        }
        for (Element e : values) {
            w.writeShort(elementResistance[e.ordinal()]);
        }
    }

    public int getResistance(Element e) {
        return elementResistance[e.ordinal()];
    }

    public void addResistance(Element e, int v) {
        elementResistance[e.ordinal()] += v;
    }

    public int getDamage(Element e) {
        return elementDamage[e.ordinal()];
    }

    public void addDamage(Element e, int v) {
        elementDamage[e.ordinal()] += v;
    }

    //region attacks
    public int getMinWeaponAttack() {
        return minWeaponAttack;
    }

    public void addMinWeaponAttack(int minWeaponAttack) {
        this.minWeaponAttack += minWeaponAttack;
    }

    public int getMaxWeaponAttack() {
        return maxWeaponAttack;
    }

    public void addMaxWeaponAttack(int maxWeaponAttack) {
        this.maxWeaponAttack += maxWeaponAttack;
    }

    public int getMinRangeAttack() {
        return minRangeAttack;
    }

    public void addMinRangeAttack(int minRangeAttack) {
        this.minRangeAttack += minRangeAttack;
    }

    public int getMaxRangeAttack() {
        return maxRangeAttack;
    }

    public void addMaxRangeAttack(int maxRangeAttack) {
        this.maxRangeAttack += maxRangeAttack;
    }

    public int getPDD() {
        return PDD;
    }

    public void addPDD(int PDD) {
        this.PDD += PDD;
    }

    public int getHitRate() {
        return hitRate;
    }

    public void addHitRate(int hitRate) {
        this.hitRate += hitRate;
    }

    public int getEvasion() {
        return evasion;
    }

    public void addEvasion(int evasion) {
        this.evasion += evasion;
    }
    //endregion
}
