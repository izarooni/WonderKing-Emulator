package com.izarooni.wkem.event;

import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.packet.magic.PacketOperations;

/**
 * @author izarooni
 */
public class PlayerEmoteRequest extends PacketRequest {

    private short playerID;
    private byte emote;

    public static EndianWriter getPlayerEmote(short playerID, byte emote) {
        EndianWriter w = new EndianWriter(5);
        w.writeShort(PacketOperations.Player_Emote.Id);
        w.write(emote);
        w.writeShort(playerID);
        return w;
    }

    @Override
    public boolean process(EndianReader reader) {
        emote = reader.readByte();
        playerID = reader.readShort();
        return true;
    }

    @Override
    public void run() {
        getUser().getPlayer().getMap().sendPacket(getPlayerEmote(playerID, emote));
    }
}
