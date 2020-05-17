package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;
import com.izarooni.wkem.service.Backbone;

/**
 * @author izarooni
 */
public class LoginRequest extends PacketRequest {

    private String username, password;

    @Override
    public void exception(Throwable t) {
        super.exception(t);
        getUser().sendPacket(LoginPacketCreator.getLoginResponse(LoginPacketCreator.LoginResponse_Error));
    }

    @Override
    public boolean process(EndianReader reader) {
        username = reader.readAsciiString(20).trim();
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

        user.sendPacket(LoginPacketCreator.getChannelList());
        getLogger().info("Login user('{}') password('{}')", username, password);
    }
}
