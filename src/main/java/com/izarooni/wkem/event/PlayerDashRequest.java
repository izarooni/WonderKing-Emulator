package com.izarooni.wkem.event;

import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.GamePacketCreator;
import com.izarooni.wkem.server.world.life.meta.Vector2D;

/**
 * [00042D10]
 *
 * @author izarooni
 */
public class PlayerDashRequest extends PacketRequest {

    private Vector2D location;
    private short playerID;
    private short a;
    private byte b, c;

    @Override
    public boolean process(EndianReader reader) {
        playerID = reader.readShort(); // 02 00
        a = reader.readShort(); // 8C 00
        b = reader.readByte(); // 01
        c = reader.readByte(); // 01
        location = new Vector2D(reader.readShort(), reader.readShort()); // 99 01, C3 02
        return true;
    }

    @Override
    public void run() {
        getUser().getPlayer().getMap().sendPacket(GamePacketCreator.getPlayerDash(playerID, a, b, c, location));
    }
}
