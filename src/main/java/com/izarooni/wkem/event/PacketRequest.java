package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.PacketReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author izarooni
 */
public abstract class PacketRequest {

    private final Logger logger;
    private User user;

    public PacketRequest() {
        logger = LoggerFactory.getLogger(getClass());
    }

    public Logger getLogger() {
        return logger;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void exception(Throwable t) {
        getLogger().error("something went wrong", t);
    }

    public abstract boolean process(PacketReader reader);

    public abstract void run();
}
