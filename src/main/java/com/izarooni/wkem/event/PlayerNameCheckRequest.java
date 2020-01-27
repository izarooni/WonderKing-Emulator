package com.izarooni.wkem.event;

import com.izarooni.wkem.packet.accessor.PacketReader;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;
import com.izarooni.wkem.server.world.life.Player;

/**
 * @author izarooni
 */
public class PlayerNameCheckRequest extends PacketRequest {

    private String username;

    @Override
    public boolean process(PacketReader reader) {
        username = reader.readAsciiString(20).trim();
        return true;
    }

    @Override
    public void run() {
        // todo username validation
        getUser().sendPacket(LoginPacketCreator.getNameCheckResponse(LoginPacketCreator.LoginResponse_Ok));
        Player player = new Player();
        player.setUsername(username);
        getUser().setPlayer(player);
    }
}
