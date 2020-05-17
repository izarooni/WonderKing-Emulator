package com.izarooni.wkem.server.world;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.codec.GameDecoder;
import com.izarooni.wkem.packet.codec.LoginEncoder;
import com.izarooni.wkem.life.Player;
import com.izarooni.wkem.service.ServerHandler;
import com.izarooni.wkem.util.Disposable;
import com.izarooni.wkem.util.PacketAnnouncer;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * @author izarooni
 */
public class Channel implements PacketAnnouncer, Disposable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Channel.class);

    private final int serverId;
    private final int id;
    private final String address;
    private final int port;
    private final NioSocketAcceptor socket;
    private final ConcurrentHashMap<Integer, Player> players;
    private final ConcurrentHashMap<Integer, Map> maps;

    public Channel(int serverId, int id, String address, int port, int capacity) throws IOException {
        this.serverId = serverId;
        this.id = id;
        this.address = address;
        this.port = port;

        socket = new NioSocketAcceptor();
        socket.getFilterChain().addLast("codec", new ProtocolCodecFilter(new LoginEncoder(), new GameDecoder()));
        socket.setHandler(new ServerHandler(this));
        socket.getSessionConfig().setTcpNoDelay(true);
        socket.bind(new InetSocketAddress(port));

        players = new ConcurrentHashMap<>((int) ((capacity / 0.75) + 1));
        maps = new ConcurrentHashMap<>(130);
    }

    @Override
    public String toString() {
        return String.format("Channel{serverId=%d, id=%d, address='%s', port=%d}", serverId, id, address, port);
    }

    @Override
    public void dispose() {
        socket.unbind();
        socket.dispose(false);

        for (Player player : players.values()) {
            player.dispose();
        }
        LOGGER.info("Channel {} disposed", (id + 1));
    }

    @Override
    public Stream<User> getUsers() {
        return players.values().stream().map(Player::getUser);
    }

    public int getServerId() {
        return serverId;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public NioSocketAcceptor getSocket() {
        return socket;
    }

    public ConcurrentHashMap<Integer, Player> getPlayers() {
        return players;
    }

    public Map getMap(int mapID) {
        return maps.computeIfAbsent(mapID, Map::new);
    }

    public Map removeMap(int mapId) {
        return maps.remove(mapId);
    }
}
