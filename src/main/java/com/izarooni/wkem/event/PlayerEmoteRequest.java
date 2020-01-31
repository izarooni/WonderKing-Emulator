package com.izarooni.wkem.event;

import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.GamePacketCreator;

/**
 * @author izarooni
 */
public class PlayerEmoteRequest extends PacketRequest {

    private short playerID;
    private byte emote;

    @Override
    public boolean process(EndianReader reader) {
        emote = reader.readByte();
        playerID = reader.readShort();
        return true;
    }

    @Override
    public void run() {
        getUser().getPlayer().getMap().sendPacket(GamePacketCreator.getPlayerEmote(playerID, emote));
    }
}
