package com.izarooni.wkem.server;

import com.izarooni.wkem.server.world.Channel;

import java.util.ArrayList;

/**
 * @author izarooni
 */
public class Server {

    private final int id;
    private final String name;
    private final ArrayList<Channel> channels;

    public Server(int id, String name) {
        this.id = id;
        this.name = name;

        channels = new ArrayList<>(30);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Channel> getChannels() {
        return channels;
    }
}
