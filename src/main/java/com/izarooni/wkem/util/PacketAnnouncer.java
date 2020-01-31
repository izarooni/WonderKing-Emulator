package com.izarooni.wkem.util;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianWriter;

import java.util.stream.Stream;

/**
 * @author izarooni
 */
public interface PacketAnnouncer {

    Stream<User> getUsers();

    default void sendPacket(EndianWriter packet) {
        getUsers().forEach(u -> u.sendPacket(packet));
    }
}
