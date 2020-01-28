package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.PacketReader;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;
import com.izarooni.wkem.server.world.life.Player;

/**
 * @author izarooni
 */
public class PlayerDeleteRequest extends PacketRequest {

    private byte loginPosition;
    private String username;

    @Override
    public boolean process(PacketReader reader) {
        loginPosition = reader.readByte();
        username = reader.readAsciiString(20).trim();
        Player player = getUser().getPlayers()[loginPosition];
        if (player == null) {
            getLogger().error("deletion of non-existing character: {}", loginPosition);
            return false;
        } else if (!player.getUsername().equals(username)) {
            getLogger().error("username mismatch at position {}. '{}' should be '{}'", loginPosition, username, player.getUsername());
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        user.sendPacket(LoginPacketCreator.getDeletePlayer(loginPosition));
        user.getPlayers()[loginPosition] = null;
    }
}
