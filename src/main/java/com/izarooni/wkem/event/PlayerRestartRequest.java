package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.packet.magic.PacketOperations;

/**
 * [004C4100]
 *
 * @author izarooni
 */
public class PlayerRestartRequest extends PacketRequest {

    @Override
    public boolean process(EndianReader reader) {
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        EndianWriter w = new EndianWriter();
        w.writeShort(PacketOperations.Player_Restart.Id);
        w.write(user.getChannel().getId());
        w.writeAsciiString(user.getPassword(), 32);
        user.sendPacket(w);
    }
}
