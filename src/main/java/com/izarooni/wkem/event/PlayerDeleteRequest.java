package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;
import com.izarooni.wkem.life.Player;
import com.izarooni.wkem.util.Trunk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author izarooni
 */
public class PlayerDeleteRequest extends PacketRequest {

    private byte loginPosition;
    private String username;

    @Override
    public boolean process(EndianReader reader) {
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

        Player player = user.getPlayers()[loginPosition];
        try (Connection con = Trunk.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("delete from players where id = ?")) {
                ps.setInt(1, player.getId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            getLogger().error("Failed to delete character '{}', ID: {}", player.getUsername(), player.getId(), e);
            return;
        }
        user.getPlayers()[loginPosition] = null;
        user.sendPacket(LoginPacketCreator.getDeletePlayer(loginPosition));
    }
}
