package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;
import com.izarooni.wkem.service.Backbone;

/**
 * @author izarooni
 */
public class PlayerReconnectRequest extends PacketRequest {

    private String username, password;
    private byte serverID, channelID;

    @Override
    public boolean process(EndianReader reader) {
        username = reader.readAsciiString(20).trim();
        serverID = reader.readByte(); // questionable
        channelID = reader.readByte();
        password = reader.readAsciiString(32).trim();

        User user = getUser();
        if (Backbone.getServers().get(channelID) == null) {
            user.sendPacket(LoginPacketCreator.getLoginResponse(LoginPacketCreator.LoginResponse_Error));
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        LoginPacketCreator result = user.login(username, password);
        if (result != LoginPacketCreator.LoginResponse_Ok) {
            user.sendPacket(LoginPacketCreator.getLoginResponse(result));
            return;
        }

        user.setChannel(Backbone.getServers().get(serverID).getChannels().get(channelID));

        user.sendPacket(LoginPacketCreator.getChannelList());
        getLogger().info("Login user('{}') password('{}')", username, password);
    }
}
