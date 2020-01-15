package com.izarooni.wkem.server.world;

import com.izarooni.wkem.server.world.life.Player;
import com.izarooni.wkem.service.ServerHandler;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author izarooni
 */
public class Channel {

    private final int id;
    private final int port;
    private final NioSocketAcceptor socket;
    private final ConcurrentHashMap<Integer, Player> players;

    public Channel(int id, int port, int capacity) throws IOException {
        this.id = id;
        this.port = port;

        socket = new NioSocketAcceptor();
        socket.setHandler(new ServerHandler(this));
        socket.getSessionConfig().setTcpNoDelay(true);
        socket.bind(new InetSocketAddress(port));

        players = new ConcurrentHashMap<>((int) ((0.75 / 100) * capacity));
    }

    public int getId() {
        return id;
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
}
