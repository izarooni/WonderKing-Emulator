package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.GamePacketCreator;
import com.izarooni.wkem.server.world.life.Player;

/**
 * @author izarooni
 */
public class PlayerAttractionRequest extends PacketRequest {

    private int playerID;

    @Override
    public boolean process(EndianReader reader) {
        playerID = reader.available() == 0 ? getUser().getPlayer().getId() : reader.readShort();
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        Player target = user.getPlayer().getMap().getPlayers().get(playerID);
        user.sendPacket(GamePacketCreator.getPlayerAttraction(target));
    }
}
