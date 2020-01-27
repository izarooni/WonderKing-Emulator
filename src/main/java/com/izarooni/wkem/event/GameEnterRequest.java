package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.PacketReader;
import com.izarooni.wkem.packet.accessor.PacketWriter;
import com.izarooni.wkem.packet.magic.GamePacketCreator;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;
import com.izarooni.wkem.packet.magic.PacketOperations;
import com.izarooni.wkem.server.world.life.Player;
import com.izarooni.wkem.service.Backbone;

/**
 * @author izarooni
 */
public class GameEnterRequest extends PacketRequest {

    private Player selectedPlayer;

    @Override
    public boolean process(PacketReader reader) {
        User user = getUser();

        String accountUsername = reader.readAsciiString(20).trim();
        String password = reader.readAsciiString(32).trim();
        byte loginPosition = reader.readByte();
        String playerUsername = reader.readAsciiString(20);

        if (loginPosition < 0 || loginPosition > 3) {
            getLogger().warn("invalid login position: {}", loginPosition);
            return false;
        }

        LoginPacketCreator result = user.login(accountUsername, password);
        Backbone.Users.put(accountUsername, user);
        if (result != LoginPacketCreator.LoginResponse_Ok) {
            getLogger().warn("incorrect credentials (acc_id: '{}', password: '{}')", accountUsername, password);
            return false;
        }
        selectedPlayer = user.getPlayers()[loginPosition];
        if (selectedPlayer == null) {
            getLogger().warn("invalid character selection: {}", loginPosition);
            return false;
        } else if (selectedPlayer.getLoginPosition() != loginPosition) {
            getLogger().warn("character selection mismatch. got {} expected {}", loginPosition, selectedPlayer.getLoginPosition());
            return false;
//        } else if (!selectedPlayer.getUsername().equals(playerUsername)) {
//            getLogger().warn("character username mismatch. got '{}', expected '{}'", playerUsername, selectedPlayer.getUsername());
//            return false;
        }
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        user.setPlayer(selectedPlayer);
        user.sendPacket(GamePacketCreator.getEnterGame(selectedPlayer));

        PacketWriter w = new PacketWriter(32);
        w.writeShort(PacketOperations.Game_Enter.Id);
        w.skip(5);
        w.write(5);
        w.skip(2);
        user.sendPacket(w);
    }
}
