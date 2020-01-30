package com.izarooni.wkem.event;

import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.GamePacketCreator;

/**
 * @author izarooni
 */
public class PlayerChatRequest extends PacketRequest {

    private short playerID;
    private String text;

    @Override
    public boolean process(EndianReader reader) {
        byte length = reader.readByte();
        playerID = reader.readShort();
        text = reader.readAsciiString(length);
        return true;
    }

    @Override
    public void run() {
        getUser().sendPacket(GamePacketCreator.getChatText(playerID, text));
    }
}
