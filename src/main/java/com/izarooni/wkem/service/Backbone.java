package com.izarooni.wkem.service;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.io.DataReader;
import com.izarooni.wkem.packet.codec.AES;
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
import java.util.HashMap;

/**
 * @author izarooni
 */
public class Backbone {

    //region debugging
    //region todo make a property file later on...
    private static final String ServerAddress = "127.0.0.1";
    private static final int ServerPort = 10001;
    //endregion
    public static final HashMap<String, User> Users = new HashMap<>(5);
    //endregion

    private static final Logger LOGGER = LoggerFactory.getLogger(Backbone.class);
    private static final ArrayList<Server> servers = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        AES.expandKey();
        DataReader.createCache();

        IoBuffer.setUseDirectBuffer(true);
        for (int i = 0; i < 1; i++) {
            Server server = new Server(i, "tespia");
            for (int j = 0; j < 1; j++) {
                int port = (ServerPort + 1) + (j + (i * 15)); // 15 channels per world
                Channel channel = new Channel(j, ServerAddress, port, 100);
                LOGGER.info("Channel {} bound to {}:{}", (channel.getId() + 1), ServerAddress, port);
                server.getChannels().add(channel);
            }
            servers.add(server);
        }

        NioSocketAcceptor server = new NioSocketAcceptor();
        server.getFilterChain().addLast("codec", new ProtocolCodecFilter(new LoginEncoder(), new LoginDecoder()));
        server.setHandler(new ServerHandler(null));
        server.bind(new InetSocketAddress(ServerPort));
        LOGGER.info("Server bound to {}:{}", ServerAddress, ServerPort);
    }

    public static ArrayList<Server> getServers() {
        return servers;
    }
}
