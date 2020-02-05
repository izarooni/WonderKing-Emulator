package com.izarooni.wkem.event;

import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.packet.magic.PacketOperations;
import com.izarooni.wkem.util.Vector2D;

/**
 * [00042D10]
 *
 * @author izarooni
 */
public class PlayerDashRequest extends PacketRequest {

    private Vector2D location;
    private short playerID;
    private short a;
    private byte b, c;

    public static EndianWriter getPlayerDash(short playerID, short a, byte b, byte c, Vector2D location) {
        EndianWriter w = new EndianWriter(8);
        w.writeShort(PacketOperations.Player_Dash.Id);
        w.writeShort(playerID);
        w.writeShort(a);
        w.write(b);
        w.write(c);
        w.writeShort(location.getX());
        w.writeShort(location.getY());
        return w;
    }

    @Override
    public boolean process(EndianReader reader) {
        playerID = reader.readShort();

        if (playerID != getUser().getPlayer().getId()) {
            return false;
        }

        a = reader.readShort();
        b = reader.readByte();
        c = reader.readByte();
        location = new Vector2D(reader.readShort(), reader.readShort());
        return true;
    }

    @Override
    public void run() {
        getUser().getPlayer().getMap().sendPacket(getPlayerDash(playerID, a, b, c, location));
    }
}
