package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.GamePacketCreator;
import com.izarooni.wkem.server.world.Map;
import com.izarooni.wkem.server.world.life.Player;

/**
 * @author izarooni
 */
public class PlayerChatRequest extends PacketRequest {

    private short playerID;
    private String text;

    @Override
    public boolean process(EndianReader reader) {
        byte length = reader.readByte();
        playerID = reader.readShort();
        text = reader.readAsciiString(length);
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        Player player = user.getPlayer();

        String cmd = text.split(" : ")[1];
        String[] args = cmd.split(" ");
        if (cmd.startsWith("/m")) {
            int mapID = Integer.parseInt(args[1]);
            Map map = user.getChannel().getMap(mapID);
            if (map != null) {
                map.addEntity(player);
            }
            return;
        } else if (cmd.equalsIgnoreCase("/whereami")) {
            text = String.format("You are in map: '%s'", player.getMap().getTemplate().name);
        } else if (cmd.equalsIgnoreCase("/test")) {
            return;
        }

        user.sendPacket(GamePacketCreator.getChatText(playerID, text));
    }
}
