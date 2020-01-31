package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.magic.GamePacketCreator;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;
import com.izarooni.wkem.server.world.Map;
import com.izarooni.wkem.server.world.life.Player;
import com.izarooni.wkem.service.Backbone;

/**
 * @author izarooni
 */
public class GameEnterRequest extends PacketRequest {

    private Player selectedPlayer;

    @Override
    public boolean process(EndianReader reader) {
        User user = getUser();

        String accountUsername = reader.readAsciiString(20).trim();
        String password = reader.readAsciiString(32).trim();
        reader.readByte();
        int loginPosition = reader.readByte();
        String playerUsername = reader.readAsciiString(20);
        playerUsername = playerUsername.substring(0, playerUsername.indexOf('\0'));

        LoginPacketCreator result = user.login(accountUsername, password);
        Backbone.Users.put(accountUsername, user);
        if (result != LoginPacketCreator.LoginResponse_Ok) {
            getLogger().warn("incorrect credentials (acc_id: '{}', password: '{}')", accountUsername, password);
            return false;
        }
        for (Player player : user.getPlayers()) {
            if (player != null && player.getUsername().equals(playerUsername)) {
                selectedPlayer = player;
                break;
            }
        }
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

        user.sendPacket(GamePacketCreator.getPlayerInfo(selectedPlayer));
        Map map = user.getChannel().getMap(selectedPlayer.getMapId());
        selectedPlayer.setMap(map);
        map.addEntity(selectedPlayer);
    }
}
