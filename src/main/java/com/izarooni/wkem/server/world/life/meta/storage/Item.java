package com.izarooni.wkem.server.world.life.meta.storage;

import com.izarooni.wkem.io.DataReader;
import com.izarooni.wkem.io.meta.TemplateItem;
import org.slf4j.LoggerFactory;

/**
 * @author izarooni
 */
public class Item {

    public enum Type {
        Disabled(0),
        Sword_1Handed(1),
        MageWeapon(3),
        Bow(4),
        Hat(5),
        Shirt(6),
        Gloves(7),
        Pants(8),
        Shoes(9),
        Cape(10),
        Hair(12),
        Eyes(13),
        Earrings(14),
        Necklace(15),
        Bracelet(16),
        Ring(17),
        Glasses(18),
        Consumable(19),
        Consumable_Buff(20),
        Special_Use(21),
        Arrow(22),
        Materials_Crafting(23),
        Special(24),
        Shield(26),
        ThrowingStars(27),
        Daggers(28),
        Mount(29),
        GM_Mount(30),
        Bracer(31),
        Bullet(32),
        Ray_Gun(33),
        Gun(34),
        Other_Gun(35),
        Fake_Gun(36),
        Spirit_Bomb(37),
        Majeong_Bomb(38),
        Element_Bomb(39),
        Land_Mine(40),
        Consumable_Heal(41),
        Fuel(42),
        Event_Item(43),
        Crafting_Books(44),
        Unused_Scrolls(45),
        Craft_Item_Info(46),
        Machine(47),
        Cookbook(48),
        Make_Item(49),
        Weapon_Stone(50),
        Armor_Stone(51),
        Set(52),
        Old_Item(53),
        Default_Shirt(55),
        Anti_Debuff(56),
        Event_Gift(57),
        Store_Stall(59),
        Wonder_Up_Crystal(60),
        Guild_Union_Stone(61),
        Skill_Reset(62),
        Stat_Reset(63),
        Nickname_Change(64),
        Teleport_Stone(65),
        Channel_Mic(66),
        Server_Mic(67),
        Bag_Plus_Item(68),
        Bag_Plus_Equip(69),
        Bag_Plus_WH(70),
        EXP_Up(72),
        Drop_Up(73),
        Face(77),
        Broom(79),
        Pet_Eggs(82),
        Pet_Food(83),
        Magic_Stone(84),
        Dont_Lose_EXP_If_Has(85),
        Saved_Location_Teleport(86),
        Weapon_Level_Up(87),
        Special_Mic(93),
        Set2(94),
        Iras_Store_Permit(95),
        Pickaxe(96),
        Moon_Creation_Signal(97),
        Special_WonderStone(98),
        Option_Stone_Acc(99),
        Monster_Card(100),
        Gold_Pickaxe(101),
        Silver_Pickaxe(102),
        Super_Pet_Food(103),
        Super_Fuel(104),
        Option_Stone_Weap(105),
        Option_Stone_Armo(106),
        Mini_Game_Token(107),
        Obsolete(108),
        Charm_Symbol(109),
        Broken_Bow(110),
        Gender_Change(111),
        Master_Artisan_Soul(112),
        Break_Out_Quest(113),
        Mount_Chest(114),
        Item_Chest(115);
        public final int Id;

        Type(int Id) {
            this.Id = Id;
        }
    }

    private final short id;
    private final TemplateItem template;
    private StorageType storageType;
    private int quantity;

    public Item(short id) {
        this(id, 1);
    }

    public Item(short id, int quantity) {
        this.id = id;
        this.quantity = quantity;

        template = DataReader.getBaseItemData().get((long) id);
        if (template == null) {
            throw new NullPointerException("invalid item: " + id);
        }
        storageType = StorageType.Equipped;
    }

    public short getId() {
        return id;
    }

    public TemplateItem getTemplate() {
        return template;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
