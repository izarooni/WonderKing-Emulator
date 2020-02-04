package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.GamePacketCreator;
import com.izarooni.wkem.server.world.life.Player;

/**
 * @author izarooni
 */
public class PlayerQuestCompleteRequest extends PacketRequest {

    private int npcID;
    private short questID;
    private byte unk;
    private int objectID;

    @Override
    public boolean process(EndianReader reader) {
        npcID = reader.readShort();
        questID = reader.readShort();
        unk = reader.readByte();
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
        user.sendPacket(GamePacketCreator.getQuestComplete((short) npcID, questID, (byte) 3));
    }
}
