package com.izarooni.wkem.server.world;

import com.izarooni.wkem.packet.codec.GameDecoder;
import com.izarooni.wkem.packet.codec.GameEncoder;
import com.izarooni.wkem.server.world.life.Player;
import com.izarooni.wkem.service.ServerHandler;
import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author izarooni
 */
public class Channel {

    private final int id;
    private final String address;
    private final int port;
    private final NioSocketAcceptor socket;
    private final ConcurrentHashMap<Integer, Player> players;

    public Channel(int id, String address, int port, int capacity) throws IOException {
        this.id = id;
        this.address = address;
        this.port = port;

        socket = new NioSocketAcceptor();
        socket.getFilterChain().addLast("codec", new ProtocolCodecFilter(new GameEncoder(), new GameDecoder()));
        socket.setHandler(new ServerHandler(this));
        socket.getSessionConfig().setTcpNoDelay(true);
        socket.bind(new InetSocketAddress(address, port));

        players = new ConcurrentHashMap<>((int) ((0.75 / 100) * capacity));
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
}
