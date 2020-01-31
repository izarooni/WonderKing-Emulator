package com.izarooni.wkem.event;

import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.GamePacketCreator;

/**
 * [000447780]
 *
 * @author izarooni
 */
public class PlayerJumpRequest extends PacketRequest {

    private short playerID;
    private short jumpAction, b;
    private long c;

    @Override
    public boolean process(EndianReader reader) {
        playerID = reader.readShort();
        jumpAction = reader.readShort();
        b = reader.readShort();
        c = reader.readLong();
        return true;
    }

    @Override
    public void run() {
        getUser().getPlayer().getMap().sendPacket(GamePacketCreator.getPlayerJump(playerID, jumpAction, b, c));
    }
}
