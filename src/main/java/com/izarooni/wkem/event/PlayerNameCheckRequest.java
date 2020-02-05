package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;
import com.izarooni.wkem.life.Player;

/**
 * @author izarooni
 */
public class PlayerNameCheckRequest extends PacketRequest {

    private String username;

    @Override
    public boolean process(EndianReader reader) {
        username = reader.readAsciiString(20).trim();
        return true;
    }

    @Override
    public void run() {
        // todo username validation
        User user = getUser();
        user.sendPacket(LoginPacketCreator.getNameCheckResponse(LoginPacketCreator.NameCheckResponse_Ok));
        Player player = new Player();
        player.setUsername(username);
        user.setPlayer(player);
        player.setUser(user);
    }
}
