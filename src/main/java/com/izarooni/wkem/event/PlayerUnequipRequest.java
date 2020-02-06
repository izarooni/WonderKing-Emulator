package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.life.Player;
import com.izarooni.wkem.life.meta.storage.Item;
import com.izarooni.wkem.life.meta.storage.Storage;
import com.izarooni.wkem.life.meta.storage.StorageType;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.packet.magic.PacketOperations;

import java.util.Optional;

/**
 * [0004C2F90]
 *
 * @author izarooni
 */
public class PlayerUnequipRequest extends PacketRequest {

    private byte unk;
    private byte slotNo;
    private int itemID;

    @Override
    public boolean process(EndianReader reader) {
        unk = reader.readByte();
        slotNo = reader.readByte();
        itemID = reader.readInt();
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        Player player = user.getPlayer();
        Storage eq = player.getStorage().get(StorageType.Equipped);

        byte dstSlot = eq.getFirstAvailableSlot();
        Optional<Item> first = eq.findItem(i -> i.getTemplate().slotNo == slotNo && i.getId() == itemID).findFirst();
        if (!first.isPresent() || dstSlot == -1 || first.get().getId() != itemID) {
            user.sendPacket(getUnequipFail());
            return;
        }
        Item item = first.get();
        user.sendPacket(getUnequip(item, unk, dstSlot));
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

    public static EndianWriter getUnequip(Item item, int unk, byte dstSlot) {
        EndianWriter w = new EndianWriter(32);
        w.writeShort(PacketOperations.Item_Unequip.Id);
        w.write(1);

        w.write(unk);
        w.writeShort(item.getId());
        w.writeInt(item.getQuantity());

        w.writeInt(0);
        w.writeShort(0);
        w.write(0);
        w.write(0);
        w.write(0);

        w.write(dstSlot);
        w.writeShort(item.getId());
        w.writeInt(item.getQuantity());

        w.writeInt(0);
        w.writeShort(0);
        w.write(0);
        w.write(0);
        w.write(0);
        return w;
    }
}
