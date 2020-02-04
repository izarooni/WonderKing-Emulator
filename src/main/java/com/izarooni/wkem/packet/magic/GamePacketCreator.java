package com.izarooni.wkem.packet.magic;

import com.izarooni.wkem.client.meta.QuestMission;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.server.world.Map;
import com.izarooni.wkem.server.world.Physics;
import com.izarooni.wkem.server.world.life.Npc;
import com.izarooni.wkem.server.world.life.Player;
import com.izarooni.wkem.server.world.life.meta.Vector2D;
import com.izarooni.wkem.server.world.life.meta.storage.StorageType;

import java.util.EnumMap;
import java.util.HashSet;

/**
 * @author izarooni
 */
public class GamePacketCreator {

    private GamePacketCreator() {
    }

    public static EndianWriter getPlayerEmote(short playerID, byte emote) {
        EndianWriter w = new EndianWriter(5);
        w.writeShort(PacketOperations.Player_Emote.Id);
        w.write(emote);
        w.writeShort(playerID);
        return w;
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
        player.encodeItemStats(w, StorageType.Equipped, 20);
        player.encodeItemStats(w, StorageType.EquippedCash, 20);

        w.skip(42);

        player.encodeStats(w);

        w.writeShort(player.getHp());
        w.writeShort(player.getMp());
        w.writeShort(player.getMaxHp());
        w.writeShort(player.getMaxMp());

        w.writeShort(1000);

        w.writeShort(10); // watk min
        w.writeShort(15); // watk max
        w.writeShort(0); // range atk min
        w.writeShort(0); // range atk max
        w.writeShort(1); // pad
        w.writeShort(38); // hit rate
        w.writeShort(6); // evasion

        w.writeShort(10);
        w.writeShort(7);
        w.writeShort(7);
        w.writeShort(7);
        w.writeShort(7);

        //region elemental damage
        w.writeShort(0); // fire
        w.writeShort(0); // water
        w.writeShort(0); // dark
        w.writeShort(0); // holy
        //endregion
        //region elemental resistance
        w.writeShort(0); // fire
        w.writeShort(0); // water
        w.writeShort(0); // dark
        w.writeShort(0); // holy
        //endregion

        w.writeFloat(Physics.XVelocity);
        w.writeFloat(Physics.YVelocity);
        w.writeShort(0); // critical
        w.writeShort(0); // bonus stats
        w.skip(44);

        w.write(1); // eqp_bags
        w.write(1); // etc_bags
        player.encodeInventory(w, StorageType.Equip, 1);
        player.encodeInventory(w, StorageType.Etc, 1);
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

    public static EndianWriter getPlayerDash(short playerID, short a, byte b, byte c, Vector2D location) {
        EndianWriter w = new EndianWriter(8);
        w.writeShort(PacketOperations.Player_Dash.Id);
        w.writeShort(playerID);
        w.writeShort(a);
        w.write(b);
        w.write(c);
        w.writeShort(location.getX());
        w.writeShort(location.getY());
        return w;
    }

    public static EndianWriter getPlayerJump(short playerID, short a, short b, long c) {
        EndianWriter w = new EndianWriter(16);
        w.writeShort(PacketOperations.Player_Jump.Id);
        w.writeShort(playerID);
        w.writeShort(a);
        w.writeShort(b);
        w.writeLong(c);
        return w;
    }

    /**
     * @param playerID unique ID of the moving player
     * @param flag1    int16
     * @param flag2    unsigned byte
     * @param flag3    int32
     */
    public static EndianWriter getPlayerMove(int playerID, short flag1, short flag2, int flag3, Vector2D location) {
        EndianWriter w = new EndianWriter(15);
        w.writeShort(PacketOperations.Player_Move.Id);
        w.writeShort(playerID);
        w.writeShort(flag1);
        w.writeShort(location.getX());
        w.writeShort(location.getY());
        w.write(flag2);
        w.writeInt(flag3);
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

    public static EndianWriter getChatText(short playerID, String text) {
        EndianWriter w = new EndianWriter(5 + text.length());
        w.writeShort(PacketOperations.Chat_Text.Id);
        w.write(text.length());
        w.writeShort(playerID);
        w.writeAsciiString(text);
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
        w.writeShort(PacketOperations.Map_Change.Id);
        w.write(4);
        return w;
    }

    // [00055320]
    public static EndianWriter getPlayerMapTransfer(Player player, Map map) {
        int npSize = map.getPlayers().size();

        EndianWriter w = new EndianWriter(14 + (npSize * 160));
        w.writeShort(PacketOperations.Map_Change.Id);
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

    public static EndianWriter getNpcAppear(Npc npc) {
        EndianWriter w = new EndianWriter(69);
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

    // [004C4B00]
    public static EndianWriter getNpcQuests(short npcID, EnumMap<QuestMission.Status, HashSet<QuestMission>> quests) {
        EndianWriter w = new EndianWriter();
        w.writeShort(PacketOperations.Npc_Talk.Id);
        w.writeShort(npcID);

        HashSet<QuestMission> q = quests.get(QuestMission.Status.Complete);
        w.write(q.size());
        q.forEach(c -> w.writeShort(c.getID()));

        q = quests.get(QuestMission.Status.InProgress);
        w.write(q.size());
        q.forEach(c -> w.writeShort(c.getID()));

        q = quests.get(QuestMission.Status.Available);
        w.write(q.size());
        q.forEach(c -> w.writeShort(c.getID()));
        return w;
    }

    // [00435660]
    private static EndianWriter getQuestStatus(PacketOperations op, short npcID, short questID, byte status) {
        EndianWriter w = new EndianWriter(10);
        w.writeShort(op.Id);
        w.writeShort(npcID);
        w.writeShort(questID);
        w.write(status);
        return w;
    }

    // [004D8070]
    public static EndianWriter getQuestReceive(short npcID, short questID, byte status) {
        EndianWriter w = getQuestStatus(PacketOperations.Quest_Receive, npcID, questID, status);
        w.write(0);
        w.writeShort(0);
        return w;
    }

    // [004D8220]
    public static EndianWriter getQuestComplete(short npcID, short questID, byte status) {
        EndianWriter w = getQuestStatus(PacketOperations.Quest_Complete, npcID, questID, status);
        w.writeShort(0);
        return w;
    }
}
