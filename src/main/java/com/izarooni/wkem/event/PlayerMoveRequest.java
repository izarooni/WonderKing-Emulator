package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.GamePacketCreator;
import com.izarooni.wkem.server.world.life.Player;
import com.izarooni.wkem.server.world.life.meta.Vector2D;

/**
 * @author izarooni
 */
public class PlayerMoveRequest extends PacketRequest {

    private int playerID;
    private Vector2D location;
    private short flag1, flag2;
    private int flag3; // unsigned?

    @Override
    public boolean process(EndianReader reader) {
        playerID = reader.readShort();

        if (playerID != getUser().getPlayer().getId()) {
            return false;
        }

        flag1 = reader.readShort();
        location = new Vector2D(reader.readShort(), reader.readShort());
        flag2 = reader.readByte();
        flag3 = reader.readInt();
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        Player player = user.getPlayer();
        player.getLocation().set(location);

        player.getMap().sendPacket(GamePacketCreator.getPlayerMove(playerID, flag1, flag2, flag3, location));
    }
}
