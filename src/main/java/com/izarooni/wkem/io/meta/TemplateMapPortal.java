package com.izarooni.wkem.io.meta;

import com.izarooni.wkem.packet.magic.GamePacketCreator;
import com.izarooni.wkem.server.world.Map;
import com.izarooni.wkem.server.world.life.Player;
import com.izarooni.wkem.server.world.life.meta.Vector2D;

import java.util.Optional;

/**
 * @author izarooni
 */
public class TemplateMapPortal {

    public Vector2D location;
    public int width, height;
    public int flag;
    public short unk1;
    public long unk2;
    public int unk3;
    public short destinationID;

    public void enter(Player player) {
        Map oldMap = player.getMap();
        Map newMap = player.getUser().getChannel().getMap(destinationID);

        Optional<TemplateMapPortal> portal = newMap.findPortal(p -> p.destinationID == oldMap.getId());
        if (portal.isPresent()) {
            player.getLocation().set(portal.get().location);
            newMap.addEntity(player);
        } else {
            player.getUser().sendPacket(GamePacketCreator.getPlayerMapTransferFailed());
        }
    }
}
