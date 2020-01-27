package com.izarooni.wkem.packet.magic;

import com.izarooni.wkem.packet.accessor.PacketWriter;
import com.izarooni.wkem.server.world.life.Player;

/**
 * @author izarooni
 */
public class GamePacketCreator {

    private GamePacketCreator() {
    }

    public static PacketWriter getEnterGame(Player player) {
        PacketWriter w = new PacketWriter(2000);
        w.writeShort(PacketOperations.Game_Enter.Id);
        w.writeInt(0);
        w.write(0);
        w.write(1);
        w.writeShort(player.getMapId()); // map
        w.writeShort(player.getId()); // player ID
        w.writeAsciiString(player.getUsername(), 20); // username
        w.write(player.getJob()); // job
        w.write(player.getLevel()); // level
        w.writeInt(0); // ?
        w.writeShort(player.getLocation().getX()); // x
        w.writeShort(player.getLocation().getY()); // y
        w.writeShort(player.getHp()); // hp
        w.writeShort(player.getMp()); // mp
        w.writeShort(1000);
        w.write(0); // job 2
        w.write(0); // job 3
        w.write(0); // job 4
        w.writeInt(player.getMoney()); // money
        for (int i = 0; i < 12; i++) {
            w.writeShort(0); // item ID
        }
        w.writeShort(player.getHair()); // hair
        w.writeShort(player.getEyes()); // face
        w.write(new byte[12]);
        for (int i = 0; i < 12; i++) {
//            w.AddByte(item.Level);
//            w.AddByte(item.RareType);
//            w.AddByte(item.AddOption);
//            w.AddByte(item.AddOption2);
//            w.AddByte(item.AddOption3);
//            w.AddInt16(item.Option);
//            w.AddInt16(item.Option2);
//            w.AddInt16(item.Option3);
            w.write(new byte[11]); // item id
        }
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
        w.writeShort(0);
        w.writeShort(0);
        w.writeShort(0);
        w.writeShort(0);
        w.writeShort(0);
        w.writeShort(0); //fire
        w.writeShort(0); //water
        w.writeShort(0); //blalba
        w.writeShort(0); //blat

        w.writeInt(0);
        w.writeInt(0);

        w.write(51);
        w.write(51);
        w.write(51);
        w.write(64);

        w.writeShort(0);
        w.writeShort(16744);
        w.writeShort(1);
        w.writeShort(0); // ap
        w.writeShort(0); // sp
        w.writeShort(1);

        w.write(new byte[38]);

        int eqpBags = 1, etcBags = 2;
        w.write(eqpBags); // equip bags
        w.write(etcBags); // etc bags
        for (int j = 0; j < eqpBags; j++) {
            for (int i = 20 * j; i < 20 * (j + 1); i++) {
                w.writeInt(0); // item id
            }
            for (int i = 20 * j; i < 20 * (j + 1); i++) {
                w.writeInt(0); // item id
            }
            for (int i = 20 * j; i < 20 * (j + 1); i++) {
//            w.AddByte(item.Level);
//            w.AddByte(item.RareType);
//            w.AddByte(item.AddOption);
//            w.AddByte(item.AddOption2);
//            w.AddByte(item.AddOption3);
//            w.AddInt16(item.Option);
//            w.AddInt16(item.Option2);
//            w.AddInt16(item.Option3);
                w.write(new byte[11]);
            }
        }

        for (int j = 0; j < etcBags; j++) {
            for (int i = 20 * j; i < 20 * (j + 1); i++) {
                w.writeInt(0); // item id
            }
            for (int i = 20 * j; i < 20 * (j + 1); i++) {
                w.writeInt(0); // item id
            }
            for (int i = 20 * j; i < 20 * (j + 1); i++) {
//            w.AddByte(item.Level);
//            w.AddByte(item.RareType);
//            w.AddByte(item.AddOption);
//            w.AddByte(item.AddOption2);
//            w.AddByte(item.AddOption3);
//            w.AddInt16(item.Option);
//            w.AddInt16(item.Option2);
//            w.AddInt16(item.Option3);
                w.write(new byte[11]);
            }
        }

        w.writeInt(1);
        w.write(new byte[302]);
        w.write(new byte[]{0x05, 0x00, 0x02, 0x35, 0x02, 0x01, 0x02, 0x29, 0x00, 0x04, 0x01, 0x7C, 0x01, 0x05, 0x01, 0x7D, 0x01, 0x06, 0x01, 0x7E, 0x01});
        return w;
    }
}
