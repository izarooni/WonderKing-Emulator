package com.izarooni.wkem.event;

import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.packet.magic.PacketOperations;

/**
 * @author izarooni
 */
public class PlayerRestartRequest extends PacketRequest {

    @Override
    public boolean process(EndianReader reader) {
        return true;
    }

    @Override
    public void run() {
        EndianWriter w = new EndianWriter();
        w.writeShort(PacketOperations.Player_Restart.Id);
        getUser().sendPacket(w);
    }
}
