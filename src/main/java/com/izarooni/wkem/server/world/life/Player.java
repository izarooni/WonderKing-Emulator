package com.izarooni.wkem.server.world.life;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.PacketWriter;
import com.izarooni.wkem.server.world.life.meta.Vector2D;
import com.izarooni.wkem.server.world.life.meta.storage.Item;
import com.izarooni.wkem.server.world.life.meta.storage.StorageType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author izarooni
 */
public class Player extends Entity {

    private User user;
    private int id;
    private String username;
    private long exp;
    private int hp, maxHp;
    private int mp, maxMp;
    private short hair, eyes;
    private short str, dex, $int, luk;
    private short vitality, wisdom;
    private int mapId;
    private int zed;
    private byte loginPosition;
    private byte level;
    private byte job;
    private byte gender;
    private Vector2D location;
    private ArrayList<Item> items;

    public Player() {
        level = 1;
        hp = maxHp = 50;
        mp = maxMp = 50;
        // 300 - tutorial map
        // 901 - event map
        mapId = 901;
        location = new Vector2D(113, 0);
        items = new ArrayList<>(30);
    }

    @Override
    public void dispose() {
    }

    @Override
    public void encode(PacketWriter w) {
    }

    public void encodeStats(PacketWriter w) {
        w.writeShort(str);
        w.writeShort(dex);
        w.writeShort($int);
        w.writeShort(vitality);
        w.writeShort(luk);
        w.writeShort(wisdom);
    }

    public void encodeBasic(PacketWriter w) {
        w.writeInt(loginPosition);
        w.writeAsciiString(username, 20);
        //region job advancements
        w.write(job);
        w.write(0);
        w.write(0);
        w.write(0);
        //endregion
        w.write(gender);
        w.writeShort(level);
        w.write(0); // exp as percentage
        encodeStats(w);
        w.writeInt(hp);
        w.writeInt(mp);
        encodeItems(w, StorageType.Equipped, 20);
        encodeItems(w, StorageType.EquippedCash, 20);
    }

    public void encodeItems(PacketWriter w, StorageType type, int count) {
        HashMap<Short, Item> h = new HashMap<>();
        items.stream().filter(i -> i.getStorageType() == type)
                .forEach(i -> h.put((short) i.getTemplate().slotNo, i));
        for (short i = 0; i < count; i++) {
            if (h.containsKey(i)) {
                w.writeShort(h.get(i).getId());
            } else {
                w.writeShort(0);
            }
        }
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

    public void encodeInventory(PacketWriter w, StorageType type, int bags) {
        HashMap<Short, Item> h = new HashMap<>();
        items.stream().filter(i -> i.getStorageType() == type)
                .forEach(i -> h.put((short) i.getTemplate().slotNo, i));

        for (short bag = 0; bag < bags; bag++) {
            final short start = (short) (20 * bag),
                    end = (short) (20 * (bag + 1));

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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
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

    public Vector2D getLocation() {
        return location;
    }

    public void setLocation(Vector2D location) {
        this.location = location;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
