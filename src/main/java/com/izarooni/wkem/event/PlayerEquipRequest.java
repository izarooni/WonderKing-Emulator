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
 * @author izarooni
 */
public class PlayerEquipRequest extends PacketRequest {

    private short storageType, itemID;
    private byte srcNo, dstNo;

    @Override
    public boolean process(EndianReader reader) {
        storageType = reader.readByte();
        srcNo = reader.readByte();
        itemID = reader.readShort();
        reader.readByte();
        dstNo = reader.readByte();
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        Player player = user.getPlayer();
        Storage eq = player.getStorage().get(StorageType.Equip);
        Storage eqd = player.getStorage().get(StorageType.Equipped);

        Optional<Item> first = eq.findItem(p -> p.getId() == itemID && p.getTemplate().slotNo == dstNo).findFirst();
        if (!first.isPresent()) {
            return;
        }
        Item item = first.get();

        EndianWriter w = new EndianWriter(32);
        w.writeShort(PacketOperations.Item_Equip.Id);
        w.write(1);

        w.write(0);
        w.write(0);
        w.write(srcNo);
        w.writeShort(item.getId());
        w.writeShort(item.getId());
        w.writeInt(item.getId());
        w.writeInt(0);
        w.writeShort(0);
        w.write(0);
        w.write(0);
        w.write(dstNo);
        w.writeShort(item.getId());
        w.writeInt(0);
        w.writeInt(0);
        w.writeShort(0);
        w.write(0);
        user.sendPacket(w);

        player.recalculate();
        user.sendPacket(GamePacketCreator.getPlayerUpdateLocalStats(player));
    }
}
