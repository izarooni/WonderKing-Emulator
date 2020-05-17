package com.izarooni.wkem.server;

import com.izarooni.wkem.server.world.Channel;

import java.util.ArrayList;

/**
 * @author izarooni
 */
public class Server {

    public static final String[] names = {"Windus", "Kadopan"};

    private final int id;
    private final String name;
    private final ArrayList<Channel> channels;

    public Server(int id) {
        this.id = id;
        this.name = names[id];

        channels = new ArrayList<>(30);
    }

    @Override
    public String toString() {
        return String.format("Server{id=%d, name='%s'}", id, name);
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
