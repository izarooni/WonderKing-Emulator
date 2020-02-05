package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.GamePacketCreator;
import com.izarooni.wkem.life.Player;

import java.util.Optional;

/**
 * @author izarooni
 */
public class PlayerViewInfoRequest extends PacketRequest {

    private String username;

    @Override
    public boolean process(EndianReader reader) {
        username = reader.readAsciiString(20).trim();
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        Player player = user.getPlayer();
        Optional<Player> target = player.getMap().findPlayer(p -> p.getUsername().equals(username));
        getUser().sendPacket(GamePacketCreator.getPlayerViewInfo(target.orElse(null)));
    }
}
