package com.izarooni.wkem.event;

import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.life.Player;

/**
 * @author izarooni
 */
public class PlayerQuitRequest extends PacketRequest {
    @Override
    public boolean process(EndianReader reader) {
        return true;
    }

    @Override
    public void run() {
        Player player = getUser().getPlayer();
        getLogger().info("'{}' has quit the game", player.getUsername());
        player.dispose();
    }
}
