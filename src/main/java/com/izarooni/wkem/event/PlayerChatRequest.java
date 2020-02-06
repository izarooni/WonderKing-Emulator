package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.life.Player;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.packet.magic.PacketOperations;
import com.izarooni.wkem.server.world.Channel;
import com.izarooni.wkem.server.world.Map;

/**
 * @author izarooni
 */
public class PlayerChatRequest extends PacketRequest {

    private short playerID;
    private String text;

    public static EndianWriter getChatText(short playerID, String text) {
        EndianWriter w = new EndianWriter(5 + text.length());
        w.writeShort(PacketOperations.Player_Chat.Id);
        w.write(text.length());
        w.writeShort(playerID);
        w.writeAsciiString(text);
        return w;
    }

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
        Channel ch = user.getChannel();
        Player player = user.getPlayer();

        String cmd = text.split(" : ")[1];
        String[] args = cmd.split(" ");
        if (cmd.startsWith("/m")) {
            int mapID = Integer.parseInt(args[1]);
            Map map = ch.getMap(mapID);
            if (map != null) {
                map.addEntity(player);
            }
            return;
        } else if (cmd.equalsIgnoreCase("/whereami")) {
            text = String.format("You are in map: '%s'", player.getMap().getTemplate().name);
        } else if (cmd.equalsIgnoreCase("/reloadmap")) {
            Map old = ch.removeMap(player.getMapId());
            Map nMap = ch.getMap(player.getMapId());
            old.getPlayers().values().forEach(nMap::addEntity);
            old.dispose();
            return;
        }

        player.getMap().sendPacket(getChatText(playerID, text));
    }
}
