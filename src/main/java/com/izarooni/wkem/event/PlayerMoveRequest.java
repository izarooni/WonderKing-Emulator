package com.izarooni.wkem.event;

import com.izarooni.wkem.packet.accessor.PacketReader;
import com.izarooni.wkem.server.world.life.Player;
import com.izarooni.wkem.server.world.life.meta.Vector2D;

/**
 * @author izarooni
 */
public class PlayerMoveRequest extends PacketRequest {

    private Vector2D location;

    @Override
    public boolean process(PacketReader reader) {
        int playerID = reader.readShort();
        int flag1 = reader.readShort();
        location = new Vector2D(reader.readShort(), reader.readShort());
        short flag2 = reader.readByte();
        int flag3 = reader.readInt();
        return true;
    }

    @Override
    public void run() {
        Player player = getUser().getPlayer();
        player.getLocation().set(location);
    }
}
