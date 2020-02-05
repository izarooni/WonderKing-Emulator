package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.life.Player;
import com.izarooni.wkem.life.meta.storage.Item;
import com.izarooni.wkem.packet.accessor.EndianReader;

import java.util.Optional;

/**
 * @author izarooni
 */
public class PlayerUnequipRequest extends PacketRequest {

    private byte unk1;
    private byte slotNo;
    private int itemID;

    @Override
    public boolean process(EndianReader reader) {
        unk1 = reader.readByte();
        slotNo = reader.readByte();
        itemID = reader.readInt();
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        Player player = user.getPlayer();
        Optional<Item> first = player.getItems().stream().filter(i -> i.getTemplate().slotNo == slotNo && i.getId() == itemID).findFirst();
        if (!first.isPresent()) {
            return;
        }

    }
}
