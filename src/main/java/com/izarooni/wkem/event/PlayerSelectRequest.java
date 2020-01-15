package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.PacketReader;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;

/**
 * @author izarooni
 */
public class PlayerSelectRequest extends PacketRequest {

    @Override
    public boolean process(PacketReader reader) {
        return true;
    }

    @Override
    public void run() {
        User user = getUser();

        user.sendPacket(LoginPacketCreator.getSelectPlayer(user));
    }
}
