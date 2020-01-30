package com.izarooni.wkem.event;

import com.izarooni.wkem.packet.accessor.EndianReader;

/**
 * @author izarooni
 */
public class PlayerMapTransferRequest extends PacketRequest {

    @Override
    public boolean process(EndianReader reader) {
        short mapID = reader.readShort();
        return false;
    }

    @Override
    public void run() {

    }
}
