package com.izarooni.wkem.packet.magic;

import com.izarooni.wkem.life.Npc;
import com.izarooni.wkem.life.Player;
import com.izarooni.wkem.life.meta.storage.StorageType;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.server.world.Map;

/**
 * @author izarooni
 */
public class GamePacketCreator {

    private GamePacketCreator() {
    }

    public static EndianWriter getPlayerInfo(Player player) {
        EndianWriter w = new EndianWriter(2000);
        w.writeShort(PacketOperations.Game_Enter.Id);
        w.write(0);
        w.write(1);
        w.writeShort(player.getMapId());

        // 732 bytes
        w.writeShort(player.getId());
        w.writeAsciiString(player.getUsername(), 20);
        w.write(player.getJob());
        w.write(player.getGender());
        w.write(player.getLevel());
        w.writeInt(0); // exp
        w.writeShort(player.getLocation().getX());
        w.writeShort(player.getLocation().getY());
        w.writeShort(player.getHp());
        w.writeShort(player.getMp());
        w.writeShort(1000);
        w.write(0); // job 2
        w.write(0); // job 3
        w.write(0); // job 4
        w.writeLong(player.getZed()); // money
        player.getStorage().get(StorageType.Equipped).encodeItemStats(w, 20);
        player.getStorage().get(StorageType.EquippedCash).encodeItemStats(w, 20);
        w.skip(42);
        player.encodeBasicStats(w);
        player.encodeStats(w);
        player.getStorage().get(StorageType.Equip).encodeInventory(w, 1);
        player.getStorage().get(StorageType.Item).encodeInventory(w, 1);
        w.skip(304);

        w.write(0); // skill_count
//        for (int i = 0; i < skill_count; i++) {
//            w.writeShort(skill_id);
//            w.write(skill_level);
//            w.writeInt(0);
//        }
        byte[] numArray = new byte[]{
                (byte) 2, (byte) 111, (byte) 2, (byte) 1,
                (byte) 2, (byte) 69, (byte) 2, (byte) 2,
                (byte) 2, (byte) 97, (byte) 2, (byte) 3,
                (byte) 2, (byte) 181, (byte) 2, (byte) 4,
                (byte) 1, (byte) 136, (byte) 0, (byte) 5,
                (byte) 1, (byte) 128, (byte) 1, (byte) 6,
                (byte) 1, (byte) 126, (byte) 1, (byte) 7,
                (byte) 2, (byte) 41, (byte) 0};

        w.write(8);
        for (int i = 0; i < 8; ++i) {
            w.write(numArray[i]);
            w.write(numArray[i + 1]);
            w.write(numArray[i + 2]);
            w.write(numArray[i + 3]);
        }
        return w;
    }

    public static EndianWriter getGameEnter() {
        EndianWriter w = new EndianWriter(6);
        w.writeShort(PacketOperations.Game_Enter.Id);
        w.write(0);
        w.write(5);
        w.writeShort(0);
        return w;
    }

    // [0004CF810]
    public static EndianWriter getPlayerViewInfo(Player player) {
        EndianWriter w = new EndianWriter();
        w.writeShort(PacketOperations.Player_ViewInfo.Id);
        w.write(player == null ? 1 : 0);
        if (player != null) {
            // 353 bytes?
            w.writeAsciiString(player.getUsername(), 20);
            w.writeShort(player.getLevel());
            w.write(player.getJob());
            w.write(0);
            w.write(0);
            w.write(0); // 25
            w.writeShort(player.getMapId()); // 27
            w.skip(17); // guild name - 44
            w.write(0); // guild title - 45
            w.writeInt(player.getAttraction()); // 46
//            w.nCopies((byte) 0, 303);
//            for (int i = 0; i < 16; i++) {
//                w.writeShort(1);
//                w.write(0);
//            }
        }
        return w;
    }

    public static EndianWriter getPlayersInMap(Map map) {
        int npSize = map.getPlayers().size();

        EndianWriter w = new EndianWriter(6 + (npSize * 160));
        w.writeShort(PacketOperations.Game_Enter.Id);
        w.write(0);
        w.write(2);

        w.writeShort(npSize);
        for (Player player : map.getPlayers().values()) {
            player.encode(w);
        }
        return w;
    }

    public static EndianWriter getPlayerAppear(Player player) {
        EndianWriter w = new EndianWriter(3);
        w.writeShort(PacketOperations.Player_Appear.Id);
        player.encode(w);
        return w;
    }


    public static EndianWriter getPlayerDisappear(Player player) {
        EndianWriter w = new EndianWriter(6);
        w.writeShort(PacketOperations.Player_Disappear.Id);
        w.writeShort(player.getId());
        w.writeShort(0);
        return w;
    }

    // [00055320]
    public static EndianWriter getPlayerMapTransferFailed() {
        EndianWriter w = new EndianWriter(3);
        w.writeShort(PacketOperations.Map_Transfer.Id);
        w.write(4);
        return w;
    }

    // [00055320]
    public static EndianWriter getPlayerMapTransfer(Player player, Map map) {
        int npSize = map.getPlayers().size();

        EndianWriter w = new EndianWriter(14 + (npSize * 160));
        w.writeShort(PacketOperations.Map_Transfer.Id);
        w.write(0);
        w.write(2);

        w.writeShort(map.getId());

        w.writeShort(player.getId());
        w.writeShort(player.getLocation().getX());
        w.writeShort(player.getLocation().getY());

        w.writeShort(npSize);
        for (Player players : map.getPlayers().values()) {
            players.encode(w);
        }
        return w;
    }

    // [0004436F0]
    public static EndianWriter getPlayerAttraction(Player player) {
        EndianWriter w = new EndianWriter(13);
        w.writeShort(PacketOperations.Player_RightClick.Id);
        w.write(player == null ? 1 : 0);
        if (player == null) {
            return w;
        }
        w.writeInt(player.getAttraction()); //      dword_99188C
        w.writeInt(0); // another points system? dword_991890
        w.writeShort(player.getLevel());
        return w;
    }

    // [004D83AA]
    public static EndianWriter getPlayerUpdateLocalStats(Player player) {
        EndianWriter w = new EndianWriter(122);
        w.writeShort(PacketOperations.Player_Update_Stats.Id);
        player.encodeBasicStats(w);
        player.encodeStats(w);
        return w;
    }

    public static EndianWriter getRemotePlayerUpdateAvatar(Player player) {
        EndianWriter w = new EndianWriter();
        w.writeShort(PacketOperations.Remote_Player_Update_Avatar.Id);
        w.writeShort(player.getId());
        player.getStorage().get(StorageType.Equipped).encodeItemIDs(w, 20);
        player.getStorage().get(StorageType.EquippedCash).encodeItemIDs(w, 20);
        w.writeFloat(player.getLocation().getX());
        w.writeFloat(player.getLocation().getY());
        return w;
    }

    public static EndianWriter getNpcAppear(Npc npc) {
        EndianWriter w = new EndianWriter(71);
        w.writeShort(PacketOperations.Npc_Appear.Id);
        npc.encode(w);
        return w;
    }

    // [00440990]
    public static EndianWriter getNpcDisappear(int objectID) {
        EndianWriter w = new EndianWriter(4);
        w.writeShort(PacketOperations.Npc_Disappear.Id);
        w.writeShort(objectID);
        // if (objectId >= 500): "%s index - %d value - %d id - %s size - %d"
        return w;
    }

    // [00435660]
    public static EndianWriter getQuestStatus(PacketOperations op, short npcID, short questID, byte status) {
        EndianWriter w = new EndianWriter(10);
        w.writeShort(op.Id);
        w.writeShort(npcID);
        w.writeShort(questID);
        w.write(status);
        return w;
    }

}
