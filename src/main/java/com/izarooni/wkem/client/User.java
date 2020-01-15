package com.izarooni.wkem.client;

import com.izarooni.wkem.packet.accessor.PacketWriter;
import com.izarooni.wkem.server.world.Channel;
import com.izarooni.wkem.server.world.life.Player;
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
    private int id;
    private String username, password;

    public User(IoSession session) {
        this.session = session;
    }

    @Override
    public void dispose() {
        session.suspendWrite();
        session.suspendRead();
        session.closeOnFlush();
        session.removeAttribute("user");
        session = null;

        if (channel != null) {
            channel.getPlayers().remove(getId());
            channel = null;
        }
    }

    public WriteFuture sendPacket(PacketWriter packet) {
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
