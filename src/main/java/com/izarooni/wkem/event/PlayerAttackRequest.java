package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.life.Player;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.packet.magic.PacketOperations;
import com.izarooni.wkem.util.Vector2D;

/**
 * @author izarooni
 */
public class PlayerAttackRequest extends PacketRequest {

    private byte direction, n_1;
    private short playerID, b, stance, e, f, g, n_0;
    private int attackCount;

    private Vector2D location;

    @Override
    public boolean process(EndianReader reader) {
        playerID = reader.readShort();
        n_0 = reader.readShort(); // always 0
        b = reader.readShort();
        stance = reader.readShort();
        direction = reader.readByte();
        n_1 = reader.readByte(); // always 1
        location = new Vector2D(reader.readShort(), reader.readShort());
        g = reader.readShort();
        attackCount = reader.readInt();
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        Player player = user.getPlayer();

        EndianWriter w = new EndianWriter();
        w.writeShort(PacketOperations.Player_Attack.Id);
        w.writeShort(playerID);
        w.writeShort(n_0); // not used?
        w.writeShort(b); // dword_9850C0[dwPlayer] + 41024
        w.writeShort(stance);
        w.write(direction);
        w.write(n_1);
        w.writeShort(location.getX());
        w.writeShort(location.getY());
        player.getMap().sendPacket(w);
    }
}
