package com.izarooni.wkem.io;

import com.izarooni.wkem.io.meta.TemplateNpc;
import com.izarooni.wkem.packet.accessor.EndianReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author izarooni
 */
public class NpcFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(NpcFactory.class);
    static HashMap<Integer, TemplateNpc> templateNpcs;

    private NpcFactory() {
    }

    public static TemplateNpc getNpc(int id) {
        return templateNpcs.get(id);
    }

    static void loadNpcStatData() throws IOException {
        File file = new File("value", "basenpcstatdata.dat");
        if (!file.exists()) {
            throw new FileNotFoundException("unable to load npc stat data");
        }
        try (FileInputStream fin = DataReader.openFile(file)) {
            EndianReader reader = new EndianReader(fin.readAllBytes());
            reader.skip(5);
            int npcCount = reader.readInt();

            templateNpcs = new HashMap<>(npcCount + 1, 1f);

            for (int i = 0; i < npcCount; i++) {
                TemplateNpc npc = new TemplateNpc();
                npc.level = reader.readInt();
                npc.id = reader.readInt();
                npc.unk1 = reader.readInt();
                npc.hp = reader.readInt();
                npc.mp = reader.readInt();
                npc.exp = reader.readInt();
                npc.speed = reader.readFloat();
                reader.skip(88);
                npc.minAtk = reader.readInt();
                npc.maxAtk = reader.readInt();
                reader.skip(80);
                npc.drops = new int[reader.readInt()];
                int dropIter;
                for (dropIter = 0; dropIter < npc.drops.length; dropIter++) {
                    npc.drops[dropIter] = reader.readInt();
                    if (dropIter == 20) break;
                }
                reader.skip(80 - ((dropIter + 1) * 4));
                reader.skip(84);
                reader.skip(80);
                npc.name = reader.readAsciiString(20);
                npc.text = reader.readAsciiString(228);
                reader.skip(792 + (9 + i * 792) - reader.getPosition());

                templateNpcs.put(npc.id, npc);
            }
        }
    }
}
