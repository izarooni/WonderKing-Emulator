package com.izarooni.wkem.life;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.client.meta.QuestMission;
import com.izarooni.wkem.io.meta.TemplateItem;
import com.izarooni.wkem.life.meta.DynamicStats;
import com.izarooni.wkem.life.meta.Element;
import com.izarooni.wkem.life.meta.storage.Item;
import com.izarooni.wkem.life.meta.storage.Storage;
import com.izarooni.wkem.life.meta.storage.StorageType;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.server.world.Map;
import com.izarooni.wkem.server.world.Physics;
import com.izarooni.wkem.util.Vector2D;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author izarooni
 */
public class Player extends Entity {

    private transient User user;
    private int id;
    private String username;
    private long exp;
    private short hair, eyes;
    private short str, dex, $int, luk;
    private short vitality, wisdom;
    private int mapId;
    private int zed;
    private int attraction;
    private byte loginPosition;
    private byte level;
    private byte job;
    private byte gender;
    private EnumMap<StorageType, Storage> storage;
    private EnumMap<QuestMission.Status, HashSet<QuestMission>> quests;
    private transient Map map;
    private transient DynamicStats dynamicStats;

    public Player() {
        setLevel((byte) 1);
        setHp(50);
        setMaxHp(50);
        setMp(50);
        setMaxMp(50);
        setMapId(300);
        setLocation(new Vector2D(113, 0));
        storage = new EnumMap<>(StorageType.class);
        for (StorageType s : StorageType.values()) {
            storage.put(s, new Storage((short) 20, (byte) 1));
        }
        quests = new EnumMap<>(QuestMission.Status.class);
        for (QuestMission.Status s : QuestMission.Status.values()) {
            quests.put(s, new HashSet<>());
        }
        setDynamicStats(dynamicStats);
    }

    @Override
    public String toString() {
        return String.format("Player{id=%d, username='%s'}", id, username);
    }

    @Override
    public void dispose() {
        if (map != null) {
            map.removeEntity(this);
        }
    }


    @Override // 160 bytes
    public void encode(EndianWriter w) {
        w.writeShort(getObjectID());
        w.writeShort(id);
        w.writeAsciiString(username, 20);
        w.write(job);
        w.write(gender);
        w.writeShort(getLocation().getX());
        w.writeShort(getLocation().getY());
        encodeItemIDs(w, StorageType.Equipped, 20).clear();
        encodeItemIDs(w, StorageType.EquippedCash, 20).clear();
        w.writeFloat(Physics.XVelocity);
        w.writeFloat(Physics.YVelocity);
        w.skip(42);
    }

    // 12 bytes
    public void encodeStats(EndianWriter w) {
        w.writeShort(str);
        w.writeShort(dex);
        w.writeShort($int);
        w.writeShort(vitality);
        w.writeShort(luk);
        w.writeShort(wisdom);
    }

    public HashMap<Short, Item> encodeItemIDs(EndianWriter w, StorageType type, int count) {
        HashMap<Short, Item> h = new HashMap<>();
        storage.get(type).forEach(i -> h.put((short) i.getTemplate().slotNo, i));
        for (short i = 0; i < count; i++) {
            if (h.containsKey(i)) w.writeShort(h.get(i).getId());
            else w.writeShort(0);
        }
        return h;
    }

    public void encodeItemStats(EndianWriter w, StorageType type, int count) {
        HashMap<Short, Item> h = encodeItemIDs(w, type, count);
        for (int i = 0; i < count; i++) {
            /*
            w.write(itemLevel);
            w.write(itemRarity);
            w.write(itemAddOption);
            w.write(itemAddOption2);
            w.write(itemAddOption3);
            w.writeShort(itemOption);
            w.writeShort(itemOption2);
            w.writeShort(itemOption3);
             */
            w.skip(11);
        }
        h.clear();
    }

    public void encodeInventory(EndianWriter w, StorageType type, int bags) {
        HashMap<Short, Item> h = new HashMap<>();
        storage.get(type).forEach(i -> h.put((short) i.getTemplate().slotNo, i));

        w.write(bags); // eqp_bags
        w.write(bags); // etc_bags

        for (short bag = 0; bag < bags; bag++) {
            final short start = (short) (20 * bag), end = (short) (20 * (bag + 1));
            for (short key = start; key < end; key++) {
                if (h.containsKey(key)) w.writeShort(h.get(key).getId());
                else w.writeShort(0);
            }
            for (short key = start; key < end; key++) {
                if (h.containsKey(key)) w.writeShort(h.get(key).getQuantity());
                else w.writeShort(0);
            }
            for (short key = start; key < end; key++) {
                /*
                w.write(itemLevel);
                w.write(itemRarity);
                w.write(itemAddOption);
                w.write(itemAddOption2);
                w.write(itemAddOption3);
                w.writeShort(itemOption);
                w.writeShort(itemOption2);
                w.writeShort(itemOption3);
                 */
                w.skip(11);
            }
        }
        h.clear();
    }

    public void recalculate() {
        setDynamicStats(dynamicStats = new DynamicStats());

        for (Item item : getStorage().get(StorageType.Equipped).getItems()) {
            TemplateItem t = item.getTemplate();
            dynamicStats.addHitRate((int) t.hitRate);
            dynamicStats.addMinWeaponAttack((int) t.minAttack);
            dynamicStats.addMaxWeaponAttack((int) t.maxAttack);
            dynamicStats.addPDD((int) t.defense);

            dynamicStats.addResistance(Element.Fire, (int) t.fireResist);
            dynamicStats.addResistance(Element.Water, (int) t.waterResist);
            dynamicStats.addResistance(Element.Dark, (int) t.darkResist);
            dynamicStats.addResistance(Element.Holy, (int) t.holyResist);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public short getHair() {
        return hair;
    }

    public void setHair(short hair) {
        this.hair = hair;
    }

    public short getEyes() {
        return eyes;
    }

    public void setEyes(short eyes) {
        this.eyes = eyes;
    }

    public short getStr() {
        return str;
    }

    public void setStr(short str) {
        this.str = str;
    }

    public short getDex() {
        return dex;
    }

    public void setDex(short dex) {
        this.dex = dex;
    }

    public short getInt() {
        return $int;
    }

    public void setInt(short $int) {
        this.$int = $int;
    }

    public short getLuk() {
        return luk;
    }

    public void setLuk(short luk) {
        this.luk = luk;
    }

    public short getVitality() {
        return vitality;
    }

    public void setVitality(short vitality) {
        this.vitality = vitality;
    }

    public short getWisdom() {
        return wisdom;
    }

    public void setWisdom(short wisdom) {
        this.wisdom = wisdom;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getZed() {
        return zed;
    }

    public void setZed(int zed) {
        this.zed = zed;
    }

    public int getAttraction() {
        return attraction;
    }

    public void setAttraction(int attraction) {
        this.attraction = attraction;
    }

    public byte getLoginPosition() {
        return loginPosition;
    }

    public void setLoginPosition(byte loginPosition) {
        this.loginPosition = loginPosition;
    }

    public byte getLevel() {
        return level;
    }

    public void setLevel(byte level) {
        this.level = level;
    }

    public byte getJob() {
        return job;
    }

    public void setJob(byte job) {
        this.job = job;
    }

    public byte getGender() {
        return gender;
    }

    public void setGender(byte gender) {
        this.gender = gender;
    }

    public EnumMap<StorageType, Storage> getStorage() {
        return storage;
    }

    public EnumMap<QuestMission.Status, HashSet<QuestMission>> getQuests() {
        return quests;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public DynamicStats getDynamicStats() {
        return dynamicStats;
    }

    public void setDynamicStats(DynamicStats dynamicStats) {
        this.dynamicStats = dynamicStats;
    }
}
