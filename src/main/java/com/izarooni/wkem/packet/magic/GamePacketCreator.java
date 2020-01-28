package com.izarooni.wkem.packet.magic;

import com.izarooni.wkem.packet.accessor.PacketWriter;
import com.izarooni.wkem.server.world.life.Player;
import com.izarooni.wkem.server.world.life.meta.storage.StorageType;

/**
 * @author izarooni
 */
public class GamePacketCreator {

    private GamePacketCreator() {
    }

    public static PacketWriter getPlayerInfo(Player player) {
        PacketWriter w = new PacketWriter(2000);
        w.writeShort(PacketOperations.Game_Enter.Id);
        w.write(0);
        w.write(1);
        w.writeShort(player.getMapId());
        w.writeShort(player.getId());
        w.writeAsciiString(player.getUsername(), 20);
        w.write(player.getJob());
        w.write(player.getGender());
        w.write(player.getLevel());
        w.writeInt(0); // exp
        w.writeShort(player.getLocation().getX());
        w.writeShort(player.getLocation().getY());
        w.writeShort(player.getHp());
        w.writeShort(player.getMp());
        w.writeShort(1000);
        w.write(0); // job 2
        w.write(0); // job 3
        w.write(0); // job 4
        w.writeLong(player.getZed()); // money
        player.encodeItems(w, StorageType.Equipped, 20);
        player.encodeItems(w, StorageType.EquippedCash, 20);

        w.skip(42);

        w.writeShort(player.getStr());
        w.writeShort(player.getDex());
        w.writeShort(player.getInt());
        w.writeShort(player.getVitality());
        w.writeShort(player.getLuk());
        w.writeShort(player.getWisdom());

        w.writeShort(player.getHp());
        w.writeShort(player.getMp());
        w.writeShort(player.getMaxHp());
        w.writeShort(player.getMaxMp());

        w.writeShort(1000);

        w.writeShort(10); // watk min
        w.writeShort(15); // watk max
        w.writeShort(0); // range atk min
        w.writeShort(0); // range atk max
        w.writeShort(1); // pad
        w.writeShort(38); // accuracy? "hit rate"
        w.writeShort(6); // evasion

        w.writeShort(10);
        w.writeShort(7);
        w.writeShort(7);
        w.writeShort(7);
        w.writeShort(7);

        w.writeShort(0); //fire
        w.writeShort(0); //water
        w.writeShort(0); //shadow
        w.writeShort(0); //holy
        w.writeInt(0); // x-velocity?
        w.writeInt(0); // y-velocity?
        w.writeShort(0); // critical
        w.writeShort(0); // bonus
        w.writeShort(0); // skill points
        w.skip(44);

        w.write(1); // eqp_bags
        w.write(1); // etc_bags

        w.skip(304);

        w.write(0); // skill_count
//        for (int i = 0; i < skill_count; i++) {
//            w.writeShort(skill_id);
//            w.write(skill_level);
//            w.writeInt(0);
//        }
        byte[] numArray = new byte[]{
                (byte) 2, (byte) 111, (byte) 2, (byte) 1,
                (byte) 2, (byte) 69, (byte) 2, (byte) 2,
                (byte) 2, (byte) 97, (byte) 2, (byte) 3,
                (byte) 2, (byte) 181, (byte) 2, (byte) 4,
                (byte) 1, (byte) 136, (byte) 0, (byte) 5,
                (byte) 1, (byte) 128, (byte) 1, (byte) 6,
                (byte) 1, (byte) 126, (byte) 1, (byte) 7,
                (byte) 2, (byte) 41, (byte) 0};

        w.write(8);
        for (int i = 0; i < 8; ++i) {
            w.write(numArray[i]);
            w.write(numArray[i + 1]);
            w.write(numArray[i + 2]);
            w.write(numArray[i + 3]);
        }
        return w;
    }

    public static PacketWriter getGameEnter() {
        PacketWriter w = new PacketWriter(32);
        w.writeShort(PacketOperations.Game_Enter.Id);
        w.write(0);
        w.write(5);
        w.writeShort(0);
        return w;
    }
}
