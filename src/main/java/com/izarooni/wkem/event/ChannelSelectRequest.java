package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.PacketReader;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;
import com.izarooni.wkem.server.world.Channel;
import com.izarooni.wkem.service.Backbone;

/**
 * @author izarooni
 */
public class ChannelSelectRequest extends PacketRequest {

    private int serverId;
    private int channelId;

    @Override
    public boolean process(PacketReader reader) {
        serverId = reader.readShort();
        channelId = reader.readShort();
        return true;
    }

    @Override
    public void run() {
        User user = getUser();

        Channel channel = Backbone.getServers().get(serverId).getChannels().get(channelId);
        user.setChannel(channel);
        user.sendPacket(LoginPacketCreator.getPlayerList(user));
    }
}
