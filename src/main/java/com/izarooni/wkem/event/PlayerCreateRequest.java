package com.izarooni.wkem.event;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.packet.magic.PacketOperations;
import com.izarooni.wkem.life.Player;
import com.izarooni.wkem.life.meta.storage.Item;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author izarooni
 */
public class PlayerCreateRequest extends PacketRequest {

    private static final AtomicInteger UID = new AtomicInteger(1);

    private byte loginPosition;
    private byte job, gender;
    private short hair, eyes, shirt, pants;

    public static EndianWriter getCreatePlayer(Player player) {
        EndianWriter w = new EndianWriter();
        w.writeShort(PacketOperations.Character_Create.Id);
        w.write(0);
        player.encodeBasic(w);
        return w;
    }

    /**
     * <ol start=1>
     *     <li>Duplicate ID</li>
     *     <li value=4>Incorrect ID</li>
     * </ol>
     */
    public static EndianWriter getCreatePlayerFailed() {
        EndianWriter w = new EndianWriter();
        w.writeShort(PacketOperations.Character_Create.Id);
        w.write(2);
        return w;
    }

    @Override
    public boolean process(EndianReader reader) {
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
            user.sendPacket(getCreatePlayerFailed());
            getLogger().warn("creation without name check");
            return false;
        } else if (!user.getPlayer().getUsername().equals(username)) {
            user.sendPacket(getCreatePlayerFailed());
            getLogger().warn("username mismatch; '{}' supposed to be '{}'", username, user.getPlayer().getUsername());
            return false;
        } else if (user.getPlayers()[loginPosition] != null) {
            user.sendPacket(getCreatePlayerFailed());
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

        if (hair < 0) {
            user.sendPacket(getCreatePlayerFailed());
            return;
        }

        player.setId(UID.getAndIncrement());
        player.setLoginPosition(loginPosition);
        player.setHair(hair);
        player.setEyes(eyes);
        player.setJob(job);
        player.setGender(gender);

        short[] stats;

        switch (job) {
            case 1: // warrior
                player.getItems().add(new Item((short) 70)); // wooden club
                stats = new short[]{13, 9, 3, 5, 17, 5};
                break;
            case 2: // mage
                player.getItems().add(new Item((short) 150)); // shabby staff
                stats = new short[]{6, 6, 14, 3, 15, 11};
                break;
            case 3: // thief
                player.getItems().add(new Item((short) 218)); // rusty dagger
                stats = new short[]{7, 11, 5, 9, 16, 6};
                break;
            case 4: // scout
                player.getItems().add(new Item((short) 299)); // shabby bow
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

        player.getItems().add(new Item((short) 767)); // (GM) uniform shoes
        player.getItems().add(new Item((short) 763)); // (GM) uniform hat

        user.getPlayers()[loginPosition] = player;
        user.sendPacket(getCreatePlayer(player));
        getLogger().info("Created player('{}')", player.getUsername());
    }
}
