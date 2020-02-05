package com.izarooni.wkem.event;

import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.packet.magic.PacketOperations;

/**
 * [000447780]
 *
 * @author izarooni
 */
public class PlayerJumpRequest extends PacketRequest {

    private short playerID;
    private short jumpAction, b;
    private long c;

    public static EndianWriter getPlayerJump(short playerID, short a, short b, long c) {
        EndianWriter w = new EndianWriter(16);
        w.writeShort(PacketOperations.Player_Jump.Id);
        w.writeShort(playerID);
        w.writeShort(a);
        w.writeShort(b);
        w.writeLong(c);
        return w;
    }

    @Override
    public boolean process(EndianReader reader) {
        playerID = reader.readShort();

        if (playerID != getUser().getPlayer().getId()) {
            return false;
        }

        jumpAction = reader.readShort();
        b = reader.readShort();
        c = reader.readLong();
        return true;
    }

    @Override
    public void run() {
        getUser().getPlayer().getMap().sendPacket(getPlayerJump(playerID, jumpAction, b, c));
    }
}
