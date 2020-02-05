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
    public long price;
    public long unk7;
    public short[] r1c;
    public long levelRequirement;
    public long sexNo;
    public long weaponSomething;
    public long unk8;
    public short[] r2c;
    public long unk9;
    public long statStr;
    public long statDex;
    public long statInt;
    public long statVit;
    public long statLuk;
    public long unk10;
    public long unk11;
    public short[] r4c;
    public short[] r5c;
    public long fireResist;
    public long waterResist;
    public long darkResist;
    public long holyResist;
    public short[] r7c;
    public short[] r6c;
    public short[] r8c;
    public long defense;
    public long unk12;
    public long unk13;
    public long unk14;
    public short[] r9c;
    public short[] r10c;
    public long addHp;
    public long addMp;
    public short[] r11c;
    public long hitRate;
    public long unk15;
    public long magicAttack;
    public long critical;
    public short[] r12c;
    public long unk16;
    public long minAttack;
    public long maxAttack;
    public long physicalDamage;
    public short[] r13c, r14c, r15c, r16c;
    public int invX, invY;
    public int invWidth, invHeight;
    public int sheetID;
    public String name;
    public String description;
    public short[] unknown;
    public int petID;
    public short[] unknown2;

    @Override
    public String toString() {
        return "TemplateItem{" +
                "ID=" + id +
                ", Disabled=" + disabled +
                ", IItemType=" + itemTypeId +
                ", Unk2=" + unk2 +
                ", Unk3=" + unk3 +
                ", ClassNo1=" + classNo1 +
                ", ClassNo2=" + classNo2 +
                ", ClassNo3=" + classNo3 +
                ", ClassNo4=" + classNo4 +
                ", SlotNo=" + slotNo +
                ", SlotNo2=" + slotNo2 +
                ", Unk4=" + unk4 +
                ", Cash=" + cash +
                ", Unk5=" + unk5 +
                ", Price=" + price +
                ", Unk7=" + unk7 +
                ", R1c=" + Arrays.toString(r1c) +
                ", LevelRequirement=" + levelRequirement +
                ", SexNo=" + sexNo +
                ", WeaponSomething=" + weaponSomething +
                ", Unk8=" + unk8 +
                ", R2c=" + Arrays.toString(r2c) +
                ", Unk9=" + unk9 +
                ", StatStr=" + statStr +
                ", StatDex=" + statDex +
                ", StatInt=" + statInt +
                ", StatVit=" + statVit +
                ", StatLuk=" + statLuk +
                ", Unk10=" + unk10 +
                ", Unk11=" + unk11 +
                ", R4c=" + Arrays.toString(r4c) +
                ", R5c=" + Arrays.toString(r5c) +
                ", FireResist=" + fireResist +
                ", WaterResist=" + waterResist +
                ", DarkResist=" + darkResist +
                ", HolyResist=" + holyResist +
                ", R6c=" + Arrays.toString(r7c) +
                ", R7c=" + Arrays.toString(r6c) +
                ", R8c=" + Arrays.toString(r8c) +
                ", Defense=" + defense +
                ", Unk12=" + unk12 +
                ", Unk13=" + unk13 +
                ", Unk14=" + unk14 +
                ", R9c=" + Arrays.toString(r9c) +
                ", R10c=" + Arrays.toString(r10c) +
                ", AdditionalHP=" + addHp +
                ", AdditionalMP=" + addMp +
                ", R11c=" + Arrays.toString(r11c) +
                ", HitRate=" + hitRate +
                ", Unk15=" + unk15 +
                ", MagicAttack=" + magicAttack +
                ", Critical=" + critical +
                ", R12c=" + Arrays.toString(r12c) +
                ", Unk16=" + unk16 +
                ", MinAttack=" + minAttack +
                ", MaxAttack=" + maxAttack +
                ", PhysicalDamage=" + physicalDamage +
                ", R13c=" + Arrays.toString(r13c) +
                ", R14c=" + Arrays.toString(r14c) +
                ", R15c=" + Arrays.toString(r15c) +
                ", R16c=" + Arrays.toString(r16c) +
                ", InvX=" + invX +
                ", InvY=" + invY +
                ", InvWidth=" + invWidth +
                ", InvHeight=" + invHeight +
                ", SheetID=" + sheetID +
                ", Name='" + name + '\'' +
                ", Description='" + description + '\'' +
                ", unknown=" + Arrays.toString(unknown) +
                ", PetID=" + petID +
                ", Unknown2=" + Arrays.toString(unknown2) +
                '}';
    }
}
