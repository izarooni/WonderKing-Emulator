package com.izarooni.wkem.io.meta;

import java.util.Arrays;

/**
 * @author izarooni
 */
public class TemplateItem {

    public long id;
    public boolean disabled;
    public long itemTypeId;
    public long unk2;
    public long unk3;
    public long classNo1;
    public long classNo2;
    public long classNo3;
    public long classNo4;
    public long slotNo;
    public long slotNo2;
    public long unk4;
    public long cash;
    public long unk5;
    public long Price;
    public long unk7;
    public byte[] r1c;
    public long levelRequirement;
    public long sexNo;
    public long weaponSomething;
    public long unk8;
    public byte[] r2c;
    public long unk9;
    public long statStr;
    public long statDex;
    public long statInt;
    public long statVit;
    public long statLuk;
    public long unk10;
    public long unk11;
    public byte[] r4c;
    public byte[] r5c;
    public long elemFire;
    public long elemWater;
    public long elemDark;
    public long elemHoly;
    public byte[] r6c;
    public byte[] r7c;
    public byte[] r8c;
    public long statDef;
    public long unk12;
    public long unk13;
    public long unk14;
    public byte[] r9c;
    public byte[] r10c;
    public long AdditionalHP;
    public long AdditionalMP;
    public byte[] r11c;
    public long HitRate;
    public long unk15;
    public long MAttk;
    public long Critical;
    public byte[] r12c;
    public long unk16;
    public long minAttack;
    public long maxAttack;
    public long PhysicalDamage;
    public byte[] r13c;
    public byte[] r14c;
    public byte[] r15c;
    public byte[] r16c;
    public int InvX;
    public int InvY;
    public int InvWidth;
    public int InvHeight;
    public int SheetID;
    public String name;
    public String descriptionc;
    public byte[] Unknown;
    public int PetID;
    public byte[] Unknown2;

    @Override
    public String toString() {
        return "TemplateItem{" +
                "id=" + id +
                ", disabled=" + disabled +
                ", IItemType=" + itemTypeId +
                ", unk2=" + unk2 +
                ", unk3=" + unk3 +
                ", classNo1=" + classNo1 +
                ", classNo2=" + classNo2 +
                ", classNo3=" + classNo3 +
                ", classNo4=" + classNo4 +
                ", slotNo=" + slotNo +
                ", slotNo2=" + slotNo2 +
                ", unk4=" + unk4 +
                ", cash=" + cash +
                ", unk5=" + unk5 +
                ", Price=" + Price +
                ", unk7=" + unk7 +
                ", r1c=" + Arrays.toString(r1c) +
                ", levelRequirement=" + levelRequirement +
                ", sexNo=" + sexNo +
                ", weaponSomething=" + weaponSomething +
                ", unk8=" + unk8 +
                ", r2c=" + Arrays.toString(r2c) +
                ", unk9=" + unk9 +
                ", statStr=" + statStr +
                ", statDex=" + statDex +
                ", statInt=" + statInt +
                ", statVit=" + statVit +
                ", statLuk=" + statLuk +
                ", unk10=" + unk10 +
                ", unk11=" + unk11 +
                ", r4c=" + Arrays.toString(r4c) +
                ", r5c=" + Arrays.toString(r5c) +
                ", elemFire=" + elemFire +
                ", elemWater=" + elemWater +
                ", elemDark=" + elemDark +
                ", elemHoly=" + elemHoly +
                ", r6c=" + Arrays.toString(r6c) +
                ", r7c=" + Arrays.toString(r7c) +
                ", r8c=" + Arrays.toString(r8c) +
                ", statDef=" + statDef +
                ", unk12=" + unk12 +
                ", unk13=" + unk13 +
                ", unk14=" + unk14 +
                ", r9c=" + Arrays.toString(r9c) +
                ", r10c=" + Arrays.toString(r10c) +
                ", AdditionalHP=" + AdditionalHP +
                ", AdditionalMP=" + AdditionalMP +
                ", r11c=" + Arrays.toString(r11c) +
                ", HitRate=" + HitRate +
                ", unk15=" + unk15 +
                ", MAttk=" + MAttk +
                ", Critical=" + Critical +
                ", r12c=" + Arrays.toString(r12c) +
                ", unk16=" + unk16 +
                ", minAttack=" + minAttack +
                ", maxAttack=" + maxAttack +
                ", PhysicalDamage=" + PhysicalDamage +
                ", r13c=" + Arrays.toString(r13c) +
                ", r14c=" + Arrays.toString(r14c) +
                ", r15c=" + Arrays.toString(r15c) +
                ", r16c=" + Arrays.toString(r16c) +
                ", InvX=" + InvX +
                ", InvY=" + InvY +
                ", InvWidth=" + InvWidth +
                ", InvHeight=" + InvHeight +
                ", SheetID=" + SheetID +
                ", name='" + name + '\'' +
                ", descriptionc='" + descriptionc + '\'' +
                ", Unknown=" + Arrays.toString(Unknown) +
                ", PetID=" + PetID +
                ", Unknown2=" + Arrays.toString(Unknown2) +
                '}';
    }
}
