package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.life.Player;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;
import com.izarooni.wkem.util.Trunk;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

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
        User user = getUser();
        LoginPacketCreator result = LoginPacketCreator.NameCheckResponse_Ok;

        if (!username.matches(Pattern.compile("^[A-Za-z0-9]{3,20}$").pattern())) {
            result = LoginPacketCreator.NameCheckResponse_Incorrect;
        } else {
            try (Connection con = Trunk.getConnection()) {
                try (PreparedStatement ps = con.prepareStatement("select count(*) as count from players where username = ?")) {
                    ps.setString(1, username);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next() && rs.getInt("count") > 0) {
                            result = LoginPacketCreator.NameCheckResponse_AlreadyExists;
                        }
                    }
                }
            } catch (SQLException e) {
                result = LoginPacketCreator.NameCheckResponse_Error;
                getLogger().error("Failed to verify username for user '{}'", user.getUsername(), e);
            }
        }

        user.sendPacket(LoginPacketCreator.getNameCheckResponse(result));
        if (result == LoginPacketCreator.NameCheckResponse_Ok) {
            Player player = new Player();
            player.setUsername(username);
            user.setPlayer(player);
            player.setUser(user);
        } else {
            user.setPlayer(null);
        }
    }
}
