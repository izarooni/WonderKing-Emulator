package com.izarooni.wkem.event;

import com.izarooni.wkem.packet.accessor.EndianReader;

/**
 * @author izarooni
 */
public class PlayerRestartRequest extends PacketRequest {

    @Override
    public boolean process(EndianReader reader) {
        getLogger().info(reader.toString());
        getLogger().info(reader.toAsciiString());
        return true;
    }

    @Override
    public void run() {
    }
}
