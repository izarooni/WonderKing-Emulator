package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.packet.magic.PacketOperations;
import com.izarooni.wkem.server.world.Channel;
import com.izarooni.wkem.server.world.Map;
import com.izarooni.wkem.life.Player;
import com.izarooni.wkem.service.Backbone;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author izarooni
 */
public class ChannelChangeRequest extends PacketRequest {

    private short channelID;

    @Override
    public boolean process(EndianReader reader) {
        channelID = reader.readShort();
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        Player player = user.getPlayer();
        Channel ch = user.getChannel();
        if (channelID == ch.getId()) {
            return;
        }

        Channel channel = Backbone.getServers().get(ch.getServerId()).getChannels().get(channelID);
        if (channel == null) {
            return;
        }
        // client socket doesn't change...
        user.setChannel(channel);
        Map newMap = channel.getMap(player.getMapId());
        user.sendPacket(getChannelChange(channelID, player)); // update client channel
        newMap.addEntity(player); // respawn character in new channel map instance
    }

    public static EndianWriter getChannelChange(int channelID, Player player) {
        ConcurrentHashMap<Integer, Player> inMap = player.getMap().getPlayers();

        EndianWriter w = new EndianWriter(14 + (inMap.size() * 160));
        w.writeShort(PacketOperations.Channel_Change.Id);
        w.write(0);
        w.write(1);
        w.writeShort(channelID);
        w.writeShort(player.getId());
        w.writeShort(player.getLocation().getX());
        w.writeShort(player.getLocation().getY());
        w.writeShort(inMap.size());
        for (Player players : inMap.values()) {
            players.encode(w);
        }
        return w;
    }
}
