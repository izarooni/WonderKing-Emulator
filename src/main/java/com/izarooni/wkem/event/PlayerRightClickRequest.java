package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.GamePacketCreator;
import com.izarooni.wkem.life.Player;

/**
 * @author izarooni
 */
public class PlayerRightClickRequest extends PacketRequest {

    private short playerID;

    @Override
    public boolean process(EndianReader reader) {
        playerID = reader.readShort();
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        Player target = user.getPlayer().getMap().getPlayers().get((int) playerID);
        user.sendPacket(GamePacketCreator.getPlayerAttraction(target));
    }
}
