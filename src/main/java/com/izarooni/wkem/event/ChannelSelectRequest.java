package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;
import com.izarooni.wkem.server.Server;
import com.izarooni.wkem.server.world.Channel;
import com.izarooni.wkem.service.Backbone;

/**
 * @author izarooni
 */
public class ChannelSelectRequest extends PacketRequest {

    private int serverId;
    private int channelId;

    private Channel channel;

    @Override
    public boolean process(EndianReader reader) {
        serverId = reader.readShort();
        channelId = reader.readShort();
        Server server = Backbone.getServers().get(serverId);
        if (server == null) {
            getLogger().error("invalid server id: {}", serverId);
            return false;
        }
        channel = server.getChannels().get(channelId);
        if (channel == null) {
            getLogger().error("invalid channel id: {}", channelId);
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        User user = getUser();

        user.setChannel(channel);
        user.sendPacket(LoginPacketCreator.getPlayerList(user));
    }
}
