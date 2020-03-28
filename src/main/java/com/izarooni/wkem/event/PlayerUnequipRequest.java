package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.life.Player;
import com.izarooni.wkem.life.meta.storage.Item;
import com.izarooni.wkem.life.meta.storage.Storage;
import com.izarooni.wkem.life.meta.storage.StorageType;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.packet.magic.GamePacketCreator;
import com.izarooni.wkem.packet.magic.PacketOperations;

import java.util.Optional;

/**
 * [0004C2F90]
 *
 * @author izarooni
 */
public class PlayerUnequipRequest extends PacketRequest {

    private byte unk, unk2, dstNo;
    private byte srcNo;
    private short itemID;

    @Override
    public boolean process(EndianReader reader) {
        unk = reader.readByte();
        srcNo = reader.readByte();
        itemID = reader.readShort();
        unk2 = reader.readByte();
        dstNo = reader.readByte();
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        Player player = user.getPlayer();
        Storage eqd = player.getStorage().get(StorageType.Equipped);
        Storage eq = player.getStorage().get(StorageType.Equip);

        byte dstSlot = eq.getFirstAvailableSlot();
        Optional<Item> first = eqd.findItem(i -> i.getTemplate().slotNo == srcNo && i.getId() == itemID).findFirst();
        if (!first.isPresent() || dstSlot == -1 || first.get().getId() != itemID) {
            user.sendPacket(getUnequipFail());
        } else {
            Item item = eqd.removeItem(first.get());
            eq.putItem(dstSlot, item);

            EndianWriter w = new EndianWriter(32);
            w.writeShort(PacketOperations.Item_Unequip.Id);
            w.write(1);

            w.write(0);

            w.write(srcNo);
            w.write(0);
//            w.writeShort(0);

            w.writeInt(0);
            w.writeInt(0);
            w.writeShort(0);
            w.write(0);

            w.write(0);
            w.write(0);

            w.write(dstSlot); // 18
            w.writeShort(item.getId());
            w.writeInt(item.getQuantity());
            w.writeInt(0);
            w.writeShort(0);
            w.write(0);

            user.sendPacket(w);
        }

        player.recalculate();
        user.sendPacket(GamePacketCreator.getPlayerUpdateLocalStats(player));
        player.getMap().sendPacket(GamePacketCreator.getRemotePlayerUpdateAvatar(player));
    }

    /**
     * "Failed to detach"
     */
    public static EndianWriter getUnequipFail() {
        EndianWriter w = new EndianWriter(3);
        w.writeShort(PacketOperations.Item_Unequip.Id);
        w.write(0);
        return w;
    }

    /*
     *(_BYTE *)packet
     *
     *((_BYTE *)packet + 2),
     *(_WORD *)((char *)packet + 3),
     *(_DWORD *)((char *)packet + 5),
     *(_DWORD *)((char *)packet + 9);
     *(_WORD *)((char *)packet + 13);
     *((_BYTE *)packet + 15);
     *
     *((_BYTE *)packet + 16);
     *((_BYTE *)packet + 17),
     *
     *((_WORD *)packet + 9),
     *((_WORD *)packet + 10),
     *(_DWORD *)((char *)packet + 22),
     *(_DWORD *)((char *)packet + 26);
     *((_WORD *)packet + 15);
     *((_BYTE *)packet + 32);
     */
}
