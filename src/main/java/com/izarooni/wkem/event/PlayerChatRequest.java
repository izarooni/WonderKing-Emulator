package com.izarooni.wkem.event;

import com.izarooni.wkem.packet.accessor.EndianReader;

/**
 * @author izarooni
 */
public class PlayerChatRequest extends PacketRequest {

    @Override
    public boolean process(EndianReader reader) {
        //   izarooni : aaaaaaaaaaaaaaaaaa½
        // 3D 00 30 2E 00 00 1F 00 00 69 7A 61 72 6F 6F 6E 69 20 3A 20 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 61 9F BD
        // failing to decode the last portion of the packet
        getLogger().info(reader.readAsciiString(reader.available()));
        return true;
    }

    @Override
    public void run() {
    }
}
