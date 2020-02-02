package com.izarooni.wkem.event;

import com.izarooni.wkem.packet.accessor.EndianReader;

/**
 * @author izarooni
 */
public class PlayerReconnectRequest extends PacketRequest {

    private String username, password;

    @Override
    public boolean process(EndianReader reader) {
        username = reader.readAsciiString(20).trim();
        getLogger().info(reader.toString());
        return true;
    }

    @Override
    public void run() {
    }
}