package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.io.meta.TemplateMapPortal;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.GamePacketCreator;
import com.izarooni.wkem.server.world.life.Player;

import java.util.Optional;

/**
 * @author izarooni
 */
public class PlayerMapTransferRequest extends PacketRequest {

    private short mapID;

    @Override
    public boolean process(EndianReader reader) {
        mapID = reader.readShort();
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        Player player = user.getPlayer();

        Optional<TemplateMapPortal> portal = player.getMap().findPortal(p -> p.destinationID == mapID);
        if (portal.isPresent()) {
            portal.get().enter(player);
        } else {
            user.sendPacket(GamePacketCreator.getPlayerMapTransferFailed());
        }
    }
}
