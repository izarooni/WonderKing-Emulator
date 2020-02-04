package com.izarooni.wkem;

import com.izarooni.wkem.server.Server;
import com.izarooni.wkem.server.world.Channel;
import com.izarooni.wkem.service.Backbone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author izarooni
 */
public class TerminateServerHook implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(TerminateServerHook.class);

    @Override
    public void run() {
        if (Backbone.getSocket() != null) {
            Backbone.getSocket().unbind();
            Backbone.getSocket().dispose(false);
            LOGGER.info("Login server closed");
        }

        if (Backbone.getServers() != null) {
            for (Server server : Backbone.getServers()) {
                for (Channel ch : server.getChannels()) {
                    ch.dispose();
                }
            }
        }
        LOGGER.info("Server termination complete");
    }
}
