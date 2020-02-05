package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.packet.magic.GamePacketCreator;
import com.izarooni.wkem.packet.magic.PacketOperations;
import com.izarooni.wkem.life.Player;

/**
 * @author izarooni
 */
public class PlayerQuestReceiveRequest extends PacketRequest {

    private int npcID;
    private short questID;
    private int objectID;

    // [004D8070]
    public static EndianWriter getQuestReceive(short npcID, short questID, byte status) {
        EndianWriter w = GamePacketCreator.getQuestStatus(PacketOperations.Quest_Receive, npcID, questID, status);
        w.write(0);
        w.writeShort(0);
        return w;
    }

    @Override
    public boolean process(EndianReader reader) {
        npcID = reader.readShort();
        questID = reader.readShort();
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
        user.sendPacket(getQuestReceive((short) npcID, questID, (byte) 3));
    }
}
