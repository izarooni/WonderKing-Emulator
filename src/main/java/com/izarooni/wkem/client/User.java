package com.izarooni.wkem.client;

import com.izarooni.wkem.io.Config;
import com.izarooni.wkem.life.Player;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;
import com.izarooni.wkem.server.world.Channel;
import com.izarooni.wkem.util.Disposable;
import com.izarooni.wkem.util.Trunk;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * @author izarooni
 */
public class User implements Disposable {

    public static final String SessionAttribute = String.format("%s.%s", User.class.getName(), "user");
    private static final Logger LOGGER = LoggerFactory.getLogger(User.class);


    private transient IoSession session;
    private transient Channel channel;
    private transient Player player;
    private final Player[] players;
    private int id;
    private String username, password;

    public User(IoSession session) {
        this.session = session;

        players = new Player[3];
    }

    @Override
    public String toString() {
        return String.format("User{id=%d, username='%s'}", id, username);
    }

    @Override
    public void dispose() {
        for (Player player : players) {
            if (player != null) {
                getChannel().getPlayers().remove(player.getId());
                player.dispose();
            }
        }
        if (session != null) {
            session.suspendWrite();
            session.suspendRead();
            session.removeAttribute(SessionAttribute);
            session = null;
        }
        channel = null;
        player = null;
    }

    public void save(Connection con) throws SQLException {
        player.save(con);
    }

    public LoginPacketCreator login(String username, String password) {
        this.username = username;
        this.password = password;

        LoginPacketCreator result = LoginPacketCreator.LoginResponse_AccountNotExist;
        try (Connection con = Trunk.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("select * from users where username = ?")) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        if (!Config.Server.AutoRegister) {
                            return result;
                        }
                        try (PreparedStatement p = con.prepareStatement("insert into users values (default, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                            p.setString(1, username);
                            p.setString(2, password);
                            if (p.executeUpdate() == 1) {
                                try (ResultSet r = p.getGeneratedKeys()) {
                                    id = r.getInt(1);
                                    LOGGER.info("auto-registered account '{}', ID: {}", username, id);
                                }
                                return LoginPacketCreator.LoginResponse_Ok;
                            }
                        }
                    }
                    boolean passwordCorrect = password.equalsIgnoreCase(rs.getString("password"));
                    result = passwordCorrect ? LoginPacketCreator.LoginResponse_Ok : LoginPacketCreator.LoginResponse_IncorrectPassword;

                    if (passwordCorrect) {
                        id = rs.getInt("id");
                        username = rs.getString("username");
                    }
                }
            }
            try (PreparedStatement ps = con.prepareStatement("select * from players where account_id = ?")) {
                ps.setInt(1, getId());
                try (ResultSet rs = ps.executeQuery()) {
                    for (int i = 0; rs.next(); i++) {
                        Player player = new Player();
                        player.load(con, rs);
                        players[i] = player;
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed to login user '{}'", username, e);
            result = LoginPacketCreator.LoginResponse_ServerInternalError;
        }
        return result;
    }

    public WriteFuture sendPacket(EndianWriter packet) {
        return session.write(packet);
    }

    public IoSession getSession() {
        return session;
    }

    public void setSession(IoSession session) {
        this.session = session;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username.isEmpty() || username.length() > 20)
            throw new RuntimeException("username of invalid length");
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
