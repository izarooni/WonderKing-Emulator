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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EnumMap;
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
        setMapId(301);
        setLocation(new Vector2D(113, 0));
        storage = new EnumMap<>(StorageType.class);
        for (StorageType s : StorageType.values()) {
            storage.put(s, new Storage(s, (short) 20, (byte) 1));
        }
        quests = new EnumMap<>(QuestMission.Status.class);
        for (QuestMission.Status s : QuestMission.Status.values()) {
            quests.put(s, new HashSet<>());
        }
        setDynamicStats(new DynamicStats());
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

    /**
     * 160 bytes
     */
    @Override
    public void encode(EndianWriter w) {
        w.writeShort(getObjectID());
        w.writeShort(id);
        w.writeAsciiString(username, 20);
        w.write(job);
        w.write(gender);
        w.writeShort(getLocation().getX());
        w.writeShort(getLocation().getY());
        getStorage().get(StorageType.Equipped).encodeItemIDs(w, 20);
        getStorage().get(StorageType.EquippedCash).encodeItemIDs(w, 20);
        w.writeFloat(Physics.XVelocity);
        w.writeFloat(Physics.YVelocity);
        w.skip(42);
    }

    /**
     * 12 bytes
     */
    public void encodeBasicStats(EndianWriter w) {
        w.writeShort(str);
        w.writeShort(dex);
        w.writeShort($int);
        w.writeShort(vitality);
        w.writeShort(luk);
        w.writeShort(wisdom);
    }

    /**
     * 108 bytes
     */
    public void encodeStats(EndianWriter w) {
        w.writeShort(getHp());
        w.writeShort(getMp());
        w.writeShort(getMaxHp());
        w.writeShort(getMaxMp());
        w.writeShort(1000);
        getDynamicStats().encodeAttack(w);
        w.writeShort(10);
        w.writeShort(7);
        w.writeShort(7);
        w.writeShort(7);
        w.writeShort(7);
        getDynamicStats().encodeElements(w);
        w.writeFloat(Physics.XVelocity);
        w.writeFloat(Physics.YVelocity);
        w.writeShort(0); // critical
        w.writeShort(0); // bonus stats
        w.writeShort(0); // skill points
        w.skip(44);
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

    public void load(Connection con, ResultSet r) throws SQLException {
        id = r.getInt("id");
        username = r.getString("username");
        exp = r.getLong("exp");
        hair = r.getShort("hair");
        eyes = r.getShort("eyes");
        str = r.getShort("str");
        dex = r.getShort("dex");
        $int = r.getShort("int");
        luk = r.getShort("luk");
        vitality = r.getShort("vitality");
        wisdom = r.getShort("wisdom");
        mapId = r.getInt("map_id");
        zed = r.getInt("money");
        attraction = r.getInt("attraction");
        level = r.getByte("level");
        job = r.getByte("job");
        gender = r.getByte("gender");

        try (PreparedStatement ps = con.prepareStatement("select * from storage where player_id = ?")) {
            ps.setInt(1, getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Item item = new Item(rs.getShort("item_id"), rs.getInt("quantity"));
                    Storage storage = this.storage.get(item.getStorageType());
                    if (item.getStorageType() == StorageType.Equipped) {
                        storage.equipItem(item);
                    } else {
                        storage.putItem(rs.getByte("inventory_slot"), item);
                    }
                }
            }
        }
    }

    public void save(Connection con) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement("update players\n" +
                "set username   = ?,\n" +
                "    exp        = ?,\n" +
                "    hair       = ?,\n" +
                "    eyes       = ?,\n" +
                "    str        = ?,\n" +
                "    dex        = ?,\n" +
                "    `int`      = ?,\n" +
                "    luk        = ?,\n" +
                "    vitality   = ?,\n" +
                "    wisdom     = ?,\n" +
                "    map_id     = ?,\n" +
                "    money      = ?,\n" +
                "    attraction = ?,\n" +
                "    level      = ?,\n" +
                "    job        = ?,\n" +
                "    gender     = ?\n" +
                "where id = ?")) {
            ps.setString(1, getUsername());
            ps.setLong(2, getExp());
            ps.setShort(3, getHair());
            ps.setShort(4, getEyes());
            ps.setShort(5, getStr());
            ps.setShort(6, getDex());
            ps.setShort(7, getInt());
            ps.setShort(8, getLuk());
            ps.setShort(9, getVitality());
            ps.setShort(10, getWisdom());
            ps.setInt(11, getMapId());
            ps.setInt(12, getZed());
            ps.setInt(13, getAttraction());
            ps.setByte(14, getLevel());
            ps.setByte(15, getJob());
            ps.setByte(16, getGender());
            ps.setInt(17, getId());
            ps.executeUpdate();
        }
        try (PreparedStatement ps = con.prepareStatement("delete from storage where player_id = ?")) {
            ps.setInt(1, getId());
            ps.executeUpdate();
        }
        for (Storage s : getStorage().values()) {
            s.save(con, getId());
        }
        con.commit();
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
