package com.izarooni.wkem.event;

import com.izarooni.wkem.packet.accessor.PacketReader;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;

/**
 * @author izarooni
 */
public class LoginRequest extends PacketRequest {

    private String username, password;

    @Override
    public boolean process(PacketReader reader) {
        username = reader.readAsciiString(20).trim();
        password = reader.readAsciiString(32).trim();
        return true;
    }

    @Override
    public void run() {
        getUser().setUsername(username);
        getUser().setPassword(password);

        getUser().sendPacket(LoginPacketCreator.getLoginSuccess());
        getLogger().info("Login attempt user('{}') password('{}')", username, password);
    }
}
