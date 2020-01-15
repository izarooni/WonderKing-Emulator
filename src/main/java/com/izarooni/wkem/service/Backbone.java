package com.izarooni.wkem.service;

import com.izarooni.wkem.io.DataReader;
import com.izarooni.wkem.packet.codec.LoginDecoder;
import com.izarooni.wkem.packet.codec.LoginEncoder;
import com.izarooni.wkem.server.Server;
import com.izarooni.wkem.server.world.Channel;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;

/**
 * @author izarooni
 */
public class Backbone {

    private static final int StartingPort = 10001;
    private static final Logger LOGGER = LoggerFactory.getLogger(Backbone.class);
    private static final ArrayList<Server> servers = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        DataReader.createCache();

        IoBuffer.setUseDirectBuffer(true);
        for (int i = 0; i < 1; i++) {
            Server server = new Server(i, "tespia");
            for (int j = 0; j < 1; j++) {
                int port = StartingPort + 1;
                port += j + (i * 31);
                Channel channel = new Channel(j, port, 100);
                LOGGER.info("Channel {} bound to port {}", (channel.getId() + 1), port);
                server.getChannels().add(channel);
            }
            servers.add(server);
        }

        NioSocketAcceptor server = new NioSocketAcceptor();
        server.getFilterChain().addLast("codec", new ProtocolCodecFilter(new LoginEncoder(), new LoginDecoder()));
        server.setHandler(new ServerHandler(null));
        server.bind(new InetSocketAddress(StartingPort));
        LOGGER.info("Server bound to port 10001");
    }

    public static ArrayList<Server> getServers() {
        return servers;
    }
}
