package com.izarooni.wkem;

import com.izarooni.wkem.life.Player;
import com.izarooni.wkem.server.Server;
import com.izarooni.wkem.server.world.Channel;
import com.izarooni.wkem.service.Backbone;
import com.izarooni.wkem.util.Trunk;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

/**
 * @author izarooni
 */
public class UserSaveTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSaveTask.class);

    @Override
    public void run() {
        for (Server server : Backbone.getServers()) {
            for (Channel channel : server.getChannels()) {
                try (Connection con = Trunk.getConnection()) {
                    con.setAutoCommit(false);
                    for (Player player : channel.getPlayers().values()) {
                        Savepoint sp = con.setSavepoint(player.getUsername());
                        try {
                            player.getUser().save(con);
                            con.releaseSavepoint(sp);
                        } catch (SQLException e) {
                            con.rollback(sp);
                            LOGGER.error("Failed to save player {}", player.getUsername(), e);
                        }
                    }
                } catch (SQLException e) {
                    LOGGER.error("Failed to obtain connection?", e);
                }
            }
        }
    }
}
