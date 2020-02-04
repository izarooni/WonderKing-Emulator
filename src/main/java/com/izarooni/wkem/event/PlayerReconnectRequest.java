package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;
import com.izarooni.wkem.service.Backbone;

/**
 * @author izarooni
 */
public class PlayerReconnectRequest extends PacketRequest {

    private byte accountID;
    private String username, password;

    @Override
    public boolean process(EndianReader reader) {
        username = reader.readAsciiString(20).trim();
        reader.readByte();
        password = reader.readAsciiString(32).trim();
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

        Backbone.Users.put(username, user);
        user.sendPacket(LoginPacketCreator.getChannelList());
        getLogger().info("Login user('{}') password('{}')", username, password);
    }
}
