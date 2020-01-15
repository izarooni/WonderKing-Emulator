package com.izarooni.wkem.service;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.event.PacketRequest;
import com.izarooni.wkem.packet.accessor.PacketReader;
import com.izarooni.wkem.packet.magic.PacketOperations;
import com.izarooni.wkem.server.world.Channel;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author izarooni
 */
public class ServerHandler extends IoHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);
    private final Channel channel;

    public ServerHandler(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        User user = new User(session);
        user.setChannel(channel);
        session.setAttribute(User.SessionAttribute, user);
        LOGGER.info("Session({}) created", session.getId());
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        LOGGER.info("Session({}) opened", session.getId());
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        User user = (User) session.removeAttribute(User.SessionAttribute);
        user.dispose();
        LOGGER.info("Session({}) closed", session.getId());
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        User user = (User) session.getAttribute(User.SessionAttribute);

        if (user == null) {
            throw new NullPointerException("no user instantiated");
        }

        PacketReader reader = (PacketReader) message;
        short header = reader.readShort();
        reader.skip(4);
        Class<? extends PacketRequest> handler = PacketOperations.handlerOf(header);
        if (handler == null) {
            LOGGER.info("No handler for packet '{}'", header);
            return;
        }
        PacketRequest request = handler.getDeclaredConstructor().newInstance();
        try {
            request.setUser(user);
            if (request.process(reader)) {
                request.run();
            }
        } catch (Throwable t) {
            request.exception(t);
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
    }
}
