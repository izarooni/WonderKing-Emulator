package com.izarooni.wkem.service;

import com.izarooni.wkem.TerminateServerHook;
import com.izarooni.wkem.client.User;
import com.izarooni.wkem.io.Config;
import com.izarooni.wkem.io.DataReader;
import com.izarooni.wkem.packet.codec.AES;
import com.izarooni.wkem.packet.codec.LoginDecoder;
import com.izarooni.wkem.packet.codec.LoginEncoder;
import com.izarooni.wkem.server.Server;
import com.izarooni.wkem.server.world.Channel;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author izarooni
 */
public class Backbone {

    //region debugging
    public static final HashMap<String, User> Users = new HashMap<>(5);
    //endregion

    private static final Logger LOGGER = LoggerFactory.getLogger(Backbone.class);
    private static final ArrayList<Server> servers = new ArrayList<>();
    private static NioSocketAcceptor socket;
    private static Config config;

    public static void main(String[] args) throws IOException {
        Runtime.getRuntime().addShutdownHook(new Thread(new TerminateServerHook(), "shutdown"));

        File sConfig = new File("server.ini");
        if (sConfig.exists()) {
            config = new Config(sConfig);
        } else {
            config = new Config("server.ini", Backbone.class.getClassLoader().getResource("server.ini"));
            LOGGER.info("Created configuration file, server will be hosted on localhost:10001 by default");
        }
        String serverAddress = config.getProperty("server", "ip", String.class);
        int serverPort = config.getProperty("server", "port", int.class);
        int nChannels = config.getProperty("server", "channels", int.class);

        if (nChannels < 1 || nChannels > 15) {
            throw new RuntimeException("Too little or many channels.");
        }

        AES.expandKey();
        DataReader.createCache();

        for (int i = 0; i < 1; i++) {
            Server server = new Server(i);
            for (int j = 0; j < nChannels; j++) {
                int port = (serverPort + 1) + (j + (i * 15)); // 15 channels per world
                Channel channel = new Channel(j, serverAddress, port, 100);
                LOGGER.info("Channel {} bound to {}:{}", (channel.getId() + 1), serverAddress, port);
                server.getChannels().add(channel);
            }
            servers.add(server);
        }

        socket = new NioSocketAcceptor();
        socket.getFilterChain().addLast("codec", new ProtocolCodecFilter(new LoginEncoder(), new LoginDecoder()));
        socket.setHandler(new ServerHandler(null));
        socket.bind(new InetSocketAddress(serverPort));
        LOGGER.info("Server bound to {}:{}", serverAddress, serverPort);
    }

    public static ArrayList<Server> getServers() {
        return servers;
    }

    public static NioSocketAcceptor getSocket() {
        return socket;
    }
}
