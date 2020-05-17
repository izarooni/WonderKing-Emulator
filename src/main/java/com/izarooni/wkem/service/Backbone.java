package com.izarooni.wkem.service;

import com.izarooni.wkem.TerminateServerHook;
import com.izarooni.wkem.UserSaveTask;
import com.izarooni.wkem.io.Config;
import com.izarooni.wkem.io.DataReader;
import com.izarooni.wkem.packet.codec.AES;
import com.izarooni.wkem.packet.codec.LoginDecoder;
import com.izarooni.wkem.packet.codec.LoginEncoder;
import com.izarooni.wkem.scheduler.TaskExecutor;
import com.izarooni.wkem.server.Server;
import com.izarooni.wkem.server.world.Channel;
import com.izarooni.wkem.util.Trunk;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author izarooni
 */
public class Backbone {

    private static final Logger LOGGER = LoggerFactory.getLogger(Backbone.class);
    private static final ArrayList<Server> servers = new ArrayList<>();
    private static NioSocketAcceptor socket;

    public static void main(String[] args) throws IOException {
        if (!Config.load(null)) {
            System.exit(0);
            return;
        }
        Runtime.getRuntime().addShutdownHook(new Thread(new TerminateServerHook(), "shutdown"));

//        String serverAddress = config.getProperty("server", "ip", String.class);
//        int serverPort = config.getProperty("server", "port", int.class);
//        int nChannels = config.getProperty("server", "channels", int.class);
        String serverAddress = Config.Server.Address;
        int serverPort = Config.Server.Port;
        int channelCount = Config.Server.ChannelCount;

        if (channelCount < 1 || channelCount > 15) {
            throw new RuntimeException("Too little or many channels.");
        }

        AES.expandKey();
        DataReader.createCache();
        try (Connection con = Trunk.getConnection()) {
            Trunk.migrate(con);
        } catch (SQLException e) {
            LOGGER.error("Failed to create local database connection", e);
            System.exit(0);
            return;
        } catch (IOException e) {
            LOGGER.error("Failed to initialize database", e);
        }

        for (int i = 0; i < 1; i++) {
            Server server = new Server(i);
            for (int j = 0; j < channelCount; j++) {
                int port = (serverPort + 1) + (j + (i * 15)); // 15 channels per world
                Channel channel = new Channel(i, j, serverAddress, port, 100);
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

        TaskExecutor.executeRepeating(new UserSaveTask(), 30000, 30000);
        LOGGER.info("Automatic user save task started");

        LOGGER.info("Server start-up complete!");
    }

    public static ArrayList<Server> getServers() {
        return servers;
    }

    public static NioSocketAcceptor getSocket() {
        return socket;
    }
}
