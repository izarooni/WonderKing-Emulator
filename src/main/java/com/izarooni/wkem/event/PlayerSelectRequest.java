package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;
import com.izarooni.wkem.server.world.life.Player;

/**
 * @author izarooni
 */
public class PlayerSelectRequest extends PacketRequest {

    private int loginPosition;
    private String username;

    @Override
    public boolean process(EndianReader reader) {
        loginPosition = reader.readByte();
        username = reader.readAsciiString(20).trim();

        Player player = getUser().getPlayers()[loginPosition];
        if (player == null) {
            getLogger().warn("starting non-existing character at position: {}", loginPosition);
            return false;
        } else if (!player.getUsername().equals(username)) {
            getLogger().warn("selected '{}' expected '{}'", username, player.getUsername());
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        Player player = user.getPlayers()[loginPosition];

        user.sendPacket(LoginPacketCreator.getSelectPlayer(user));
    }
}
