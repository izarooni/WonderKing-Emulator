package com.izarooni.wkem.event;

import com.izarooni.wkem.packet.accessor.PacketReader;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;

/**
 * @author izarooni
 */
public class PlayerDeleteRequest extends PacketRequest {

    private byte loginPosition;
    private String username;

    @Override
    public boolean process(PacketReader reader) {
        loginPosition = reader.readByte();
        username = reader.readAsciiString(20);
        return true;
    }

    @Override
    public void run() {
        getUser().sendPacket(LoginPacketCreator.getDeletePlayer(loginPosition));
    }
}
