package com.izarooni.wkem.event;

import com.izarooni.wkem.packet.accessor.EndianReader;

/**
 * @author izarooni
 */
public class PlayerReconnectRequest extends PacketRequest {
    @Override
    public boolean process(EndianReader reader) {
        getLogger().info(reader.toString());
        return true;
    }

    @Override
    public void run() {
    }
}
