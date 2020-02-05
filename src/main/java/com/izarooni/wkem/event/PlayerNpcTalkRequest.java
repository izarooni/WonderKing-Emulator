package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.client.meta.QuestMission;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.packet.magic.PacketOperations;
import com.izarooni.wkem.life.Player;

import java.util.EnumMap;
import java.util.HashSet;

/**
 * @author izarooni
 */
public class PlayerNpcTalkRequest extends PacketRequest {

    private int npcID;
    private int objectID;

    // [004C4B00]
    public static EndianWriter getNpcQuests(short npcID, EnumMap<QuestMission.Status, HashSet<QuestMission>> quests) {
        EndianWriter w = new EndianWriter();
        w.writeShort(PacketOperations.Npc_Talk.Id);
        w.writeShort(npcID);

        HashSet<QuestMission> q = quests.get(QuestMission.Status.Complete);
        w.write(q.size());
        q.forEach(c -> w.writeShort(c.getID()));

        q = quests.get(QuestMission.Status.InProgress);
        w.write(q.size());
        q.forEach(c -> w.writeShort(c.getID()));

        q = quests.get(QuestMission.Status.Available);
        w.write(q.size());
        q.forEach(c -> w.writeShort(c.getID()));
        return w;
    }

    @Override
    public boolean process(EndianReader reader) {
        npcID = reader.readShort();
        objectID = reader.readShort();
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        Player player = user.getPlayer();

        if (!player.getMap().getNpcs().containsKey(objectID)) {
            getLogger().warn("NPC {} not found in map {}", npcID, player.getMap());
            return;
        }
        user.sendPacket(getNpcQuests((short) npcID, player.getQuests()));
    }
}
