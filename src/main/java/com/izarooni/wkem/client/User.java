package com.izarooni.wkem.client;

import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;
import com.izarooni.wkem.server.world.Channel;
import com.izarooni.wkem.server.world.life.Player;
import com.izarooni.wkem.service.Backbone;
import com.izarooni.wkem.util.Disposable;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author izarooni
 */
public class User implements Disposable {

    public static final String SessionAttribute = String.format("%s.%s", User.class.getName(), "user");
    private static final Logger LOGGER = LoggerFactory.getLogger(User.class);

    private IoSession session;
    private Channel channel;
    private Player player;
    private final Player[] players;
    private int id;
    private String username, password;

    public User(IoSession session) {
        this.session = session;

        players = new Player[3];
    }

    @Override
    public void dispose() {
        if (session != null) {
            session.removeAttribute(SessionAttribute);
            session.suspendWrite();
            session.suspendRead();
            session = null;
        }
        if (channel != null) {
            channel.getPlayers().remove(getId());
            channel = null;
        }
        for (Player player : players) {
            if (player != null) {
                player.dispose();
            }
        }
        player = null;
    }

    public LoginPacketCreator login(String username, String password) {
        this.username = username;
        this.password = password;

        LoginPacketCreator result = LoginPacketCreator.LoginResponse_Ok;
        User user = Backbone.Users.get(username);
        if (user != null) {
            if (user.password.equals(password)) {
                for (int i = 0; i < user.players.length; i++) {
                    players[i] = user.players[i];
                    if (players[i] != null) players[i].setUser(this);
                }
                user.dispose();
            } else {
                result = LoginPacketCreator.LoginResponse_IncorrectPassword;
            }
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
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
