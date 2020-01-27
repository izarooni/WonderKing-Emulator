package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.PacketReader;
import com.izarooni.wkem.packet.magic.LoginPacketCreator;
import com.izarooni.wkem.server.world.life.Player;
import com.izarooni.wkem.server.world.life.meta.storage.Item;

/**
 * @author izarooni
 */
public class PlayerCreateRequest extends PacketRequest {

    private byte loginPosition;
    private byte job, gender;
    private short hair, eyes, shirt, pants;

    @Override
    public boolean process(PacketReader reader) {
        loginPosition = reader.readByte();
        reader.readByte();
        reader.readShort();

        String username = reader.readAsciiString(20).trim();
        job = reader.readByte();
        gender = reader.readByte();
        hair = reader.readByte();
        eyes = reader.readByte();
        shirt = reader.readByte();
        pants = reader.readByte();

        User user = getUser();
        if (user.getPlayer() == null) {
            getLogger().warn("creation without name check");
            return false;
        } else if (!user.getPlayer().getUsername().equals(username)) {
            getLogger().warn("username mismatch; '{}' supposed to be '{}'", username, user.getPlayer().getUsername());
            return false;
        } else if (user.getPlayers()[loginPosition] != null) {
            getLogger().warn("creating character in position where one already exists: {}", loginPosition);
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        User user = getUser();
        Player player = user.getPlayer();

        hair = (short) ((job - 1) * 6 + (gender - 1) * 3 + hair + 1);
        eyes = (short) ((job - 1) * 6 + (gender - 1) * 3 + eyes + 25);
        shirt = (short) ((gender - 1) * 3 + shirt + 49);
        pants = (short) ((gender - 1) * 3 + pants + 58);

        player.setLoginPosition(loginPosition);
        player.setHair(hair);
        player.setEyes(eyes);
        player.setJob(job);
        player.setGender(gender);

        short[] stats;

        switch (job) {
            case 1:
                stats = new short[]{13, 9, 3, 5, 17, 5};
                break;
            case 2:
                stats = new short[]{6, 6, 14, 3, 15, 11};
                break;
            case 3:
                stats = new short[]{7, 11, 5, 9, 16, 6};
                break;
            case 4:
                stats = new short[]{6, 13, 8, 6, 8, 7};
                break;
            default:
                throw new RuntimeException("invalid job: " + job);
        }

        player.setStr(stats[0]);
        player.setDex(stats[1]);
        player.setInt(stats[2]);
        player.setLuk(stats[3]);
        player.setVitality(stats[4]);
        player.setWisdom(stats[5]);

        player.getItems().add(new Item(hair));
        player.getItems().add(new Item(eyes));
        player.getItems().add(new Item(shirt));
        player.getItems().add(new Item(pants));

        user.sendPacket(LoginPacketCreator.getCreatePlayer(player));
        getLogger().info("Created player('{}')", player.getUsername());
        player.setUser(user);
        user.getPlayers()[loginPosition] = player;
    }
}
