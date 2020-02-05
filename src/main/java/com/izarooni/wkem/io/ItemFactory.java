package com.izarooni.wkem.io;

import com.izarooni.wkem.io.meta.TemplateItem;
import com.izarooni.wkem.packet.accessor.EndianReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author izarooni
 */
public class ItemFactory {

    static HashMap<Long, TemplateItem> baseItemData;

    public static Stream<TemplateItem> findItems(String name) {
        return baseItemData.values().stream().filter(i -> Pattern.compile(name, Pattern.CASE_INSENSITIVE).matcher(i.name).find());
    }

    public static TemplateItem getBaseItem(short itemID) {
        return baseItemData.get((long) itemID);
    }

    static void loadBaseItemData() throws IOException {
        if (baseItemData != null) {
            baseItemData.clear();
            System.gc();
        }
        FileInputStream fis = DataReader.openFile(new File("value", "baseitemdata.dat"));
        byte[] buffer = fis.readAllBytes();
        EndianReader r = new EndianReader(buffer);
        r.skip(5);
        int count = r.readInt(); // supposed to be uint32 but we'll never exceed signed
        baseItemData = new HashMap<>(count, 1.1f);
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
            t.price = r.readUnsignedInt();
            t.unk7 = r.readUnsignedInt();
            t.r1c = r.readUnsigned(16);
            t.levelRequirement = r.readUnsignedInt();
            t.sexNo = r.readUnsignedInt();
            t.weaponSomething = r.readUnsignedInt();
            t.unk8 = r.readUnsignedInt();
            t.r2c = r.readUnsigned(16);
            t.unk9 = r.readUnsignedInt();
            t.statStr = r.readUnsignedInt();
            t.statDex = r.readUnsignedInt();
            t.statInt = r.readUnsignedInt();
            t.statVit = r.readUnsignedInt();
            t.statLuk = r.readUnsignedInt();
            t.unk10 = r.readUnsignedInt();
            t.unk11 = r.readUnsignedInt();
            t.r4c = r.readUnsigned(16);
            t.r5c = r.readUnsigned(12);
            t.r6c = r.readUnsigned(16);
            t.fireResist = r.readUnsignedInt();
            t.waterResist = r.readUnsignedInt();
            t.darkResist = r.readUnsignedInt();
            t.holyResist = r.readUnsignedInt();
            t.r7c = r.readUnsigned(4);
            t.r8c = r.readUnsigned(16);
            t.defense = r.readUnsignedInt();
            t.unk12 = r.readUnsignedInt();
            t.unk13 = r.readUnsignedInt();
            t.unk14 = r.readUnsignedInt();
            t.r9c = r.readUnsigned(16);
            t.r10c = r.readUnsigned(8);
            t.addHp = r.readUnsignedInt();
            t.addMp = r.readUnsignedInt();
            t.r11c = r.readUnsigned(12);
            t.hitRate = r.readUnsignedInt();
            t.unk15 = r.readUnsignedInt();
            t.magicAttack = r.readUnsignedInt();
            t.critical = r.readUnsignedInt();
            t.r12c = r.readUnsigned(4);
            t.unk16 = r.readUnsignedInt();
            t.minAttack = r.readUnsignedInt();
            t.maxAttack = r.readUnsignedInt();
            t.physicalDamage = r.readUnsignedInt();
            t.r13c = r.readUnsigned(16);
            t.r14c = r.readUnsigned(16);
            t.r15c = r.readUnsigned(16);
            t.r16c = r.readUnsigned(20);
            t.invX = r.readInt();
            t.invY = r.readInt();
            t.invWidth = r.readInt();
            t.invHeight = r.readInt();
            t.sheetID = r.readInt();
            t.name = r.readAsciiString(20).trim();
            t.description = r.readAsciiString(66).trim();
            t.unknown = r.readUnsigned(365);
            t.petID = r.readByte();
            t.unknown2 = r.readUnsigned(72);
            baseItemData.put(t.id, t);
        }
    }
}
