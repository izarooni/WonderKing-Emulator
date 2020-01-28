package com.izarooni.wkem.event;

import com.izarooni.wkem.packet.accessor.PacketReader;
import com.izarooni.wkem.packet.accessor.PacketWriter;
import com.izarooni.wkem.packet.magic.PacketOperations;
import com.izarooni.wkem.server.world.life.Player;
import com.izarooni.wkem.server.world.life.meta.Vector2D;

/**
 * @author izarooni
 */
public class PlayerMoveRequest extends PacketRequest {

    private Vector2D location;

    @Override
    public boolean process(PacketReader reader) {
        reader.skip(4);
        location = new Vector2D(reader.readShort(), reader.readShort());

        PacketWriter w = new PacketWriter();
        w.write(reader.array());
        getUser().sendPacket(w);
        return true;
    }

    @Override
    public void run() {
        Player player = getUser().getPlayer();
        player.getLocation().set(location);
    }
}
