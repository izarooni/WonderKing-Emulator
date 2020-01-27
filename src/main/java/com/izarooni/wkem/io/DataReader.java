package com.izarooni.wkem.io;

import com.izarooni.wkem.io.meta.TemplateItem;
import com.izarooni.wkem.packet.accessor.PacketReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author izarooni
 * @author ProjectInfinity
 */
public class DataReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataReader.class);
    private static HashMap<Long, TemplateItem> baseItemData;

    public static HashMap<Long, TemplateItem> getBaseItemData() {
        return baseItemData;
    }

    public static void createCache() throws IOException {
        loadBaseItemData();
        LOGGER.info("{} 'baseitemdata' entries cached", baseItemData.size());
    }

    public static void loadBaseItemData() throws IOException {
        if (baseItemData != null) {
            baseItemData.clear();
            System.gc();
        }
        FileInputStream fis = new FileInputStream("baseitemdata.dat");
        byte[] buffer = fis.readAllBytes();
        magic_xor(buffer, buffer.length);
        PacketReader r = new PacketReader(buffer);
        r.seek(5);
        long count = r.readUnsignedInt();
        baseItemData = new HashMap<>((int) ((0.75 / 100) * count));
        for (int i = 0; i < count; ++i) {
            r.seek(9 + i * 932);
            TemplateItem t = new TemplateItem();
            t.id = r.readUnsignedInt();
            t.disabled = r.readUnsignedInt() == 1;
            t.itemTypeId = r.readUnsignedInt();
            t.unk2 = r.readUnsignedInt();
            t.unk3 = r.readUnsignedInt();
            t.classNo1 = r.readUnsignedInt();
            t.classNo2 = r.readUnsignedInt();
            t.classNo3 = r.readUnsignedInt();
            t.classNo4 = r.readUnsignedInt();
            t.slotNo = r.readUnsignedInt();
            t.slotNo2 = r.readUnsignedInt();
            t.unk4 = r.readUnsignedInt();
            t.cash = r.readUnsignedInt();
            t.unk5 = r.readUnsignedInt();
            t.Price = r.readUnsignedInt();
            t.unk7 = r.readUnsignedInt();
            t.r1c = r.read(16);
            t.levelRequirement = r.readUnsignedInt();
            t.sexNo = r.readUnsignedInt();
            t.weaponSomething = r.readUnsignedInt();
            t.unk8 = r.readUnsignedInt();
            t.r2c = r.read(16);
            t.unk9 = r.readUnsignedInt();
            t.statStr = r.readUnsignedInt();
            t.statDex = r.readUnsignedInt();
            t.statInt = r.readUnsignedInt();
            t.statVit = r.readUnsignedInt();
            t.statLuk = r.readUnsignedInt();
            t.unk10 = r.readUnsignedInt();
            t.unk11 = r.readUnsignedInt();
            t.r4c = r.read(16);
            t.r5c = r.read(12);
            t.elemFire = r.readUnsignedInt();
            t.elemWater = r.readUnsignedInt();
            t.elemDark = r.readUnsignedInt();
            t.elemHoly = r.readUnsignedInt();
            t.r6c = r.read(4);
            t.r7c = r.read(16);
            t.r8c = r.read(16);
            t.statDef = r.readUnsignedInt();
            t.unk12 = r.readUnsignedInt();
            t.unk13 = r.readUnsignedInt();
            t.unk14 = r.readUnsignedInt();
            t.r9c = r.read(16);
            t.r10c = r.read(8);
            t.AdditionalHP = r.readUnsignedInt();
            t.AdditionalMP = r.readUnsignedInt();
            t.r11c = r.read(12);
            t.HitRate = r.readUnsignedInt();
            t.unk15 = r.readUnsignedInt();
            t.MAttk = r.readUnsignedInt();
            t.Critical = r.readUnsignedInt();
            t.r12c = r.read(4);
            t.unk16 = r.readUnsignedInt();
            t.minAttack = r.readUnsignedInt();
            t.maxAttack = r.readUnsignedInt();
            t.PhysicalDamage = r.readUnsignedInt();
            t.r13c = r.read(16);
            t.r14c = r.read(16);
            t.r15c = r.read(16);
            t.r16c = r.read(20);
            t.InvX = r.readInt();
            t.InvY = r.readInt();
            t.InvWidth = r.readInt();
            t.InvHeight = r.readInt();
            t.SheetID = r.readInt();
            t.name = r.readAsciiString(20);
            t.descriptionc = r.readAsciiString(66);
            t.Unknown = r.read(365);
            t.PetID = r.readByte();
            t.Unknown2 = r.read(72);
            baseItemData.put(t.id, t);
        }
    }

    private static void magic_xor(byte[] dat, int fileSize) {
        for (int i = 0; i < fileSize; ++i) {
            dat[i] = (byte) (dat[i] ^ 197);
        }
    }
}
