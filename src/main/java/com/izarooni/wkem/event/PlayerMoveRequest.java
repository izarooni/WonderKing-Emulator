package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.packet.magic.PacketOperations;
import com.izarooni.wkem.life.Player;
import com.izarooni.wkem.util.Vector2D;

/**
 * @author izarooni
 */
public class PlayerMoveRequest extends PacketRequest {

    private int playerID;
    private Vector2D location;
    private short flag1, flag2;
    private int flag3; // unsigned?

    /**
     * @param playerID unique ID of the moving player
     * @param flag1    int16
     * @param flag2    unsigned byte
     * @param flag3    int32
     */
    public static EndianWriter getPlayerMove(int playerID, short flag1, short flag2, int flag3, Vector2D location) {
        EndianWriter w = new EndianWriter(15);
        w.writeShort(PacketOperations.Player_Move.Id);
        w.writeShort(playerID);
        w.writeShort(flag1);
        w.writeShort(location.getX());
        w.writeShort(location.getY());
        w.write(flag2);
        w.writeInt(flag3);
        return w;
    }

    @Override
    public boolean process(EndianReader reader) {
        playerID = reader.readShort();

        if (playerID != getUser().getPlayer().getId()) {
            return false;
        }

        flag1 = reader.readShort();
        location = new Vector2D(reader.readShort(), reader.readShort());
        flag2 = reader.readByte();
        flag3 = reader.readInt();
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        Player player = user.getPlayer();
        player.getLocation().set(location);

        player.getMap().sendPacket(getPlayerMove(playerID, flag1, flag2, flag3, location));
    }
}
