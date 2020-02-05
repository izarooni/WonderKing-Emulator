package com.izarooni.wkem.packet.magic;

import com.izarooni.wkem.event.*;

public enum PacketOperations {

    Unk0x0000(0),
    Unk0x0001(1),
    Unk0x0002(2),
    Unk0x0003(3),
    Unk0x0004(4),
    Unk0x0005(5),
    Unk0x0006(6),
    Unk0x0007(7),
    Unk0x0008(8),
    Unk0x0009(9),
    Unk0x000A(10),
    Login_Info(11),
    Login_Response(12),
    Channel_Select(13),
    Character_Name_Check(14),
    Character_Create(15),
    Character_Delete(16),
    Character_Select(17),
    Player_Reconnect(18),
    Character_Guild(19),
    Unk0x0014(20),
    LoginModules(21),
    Unk0x0016(22),
    Unk0x0017(23),
    Unk0x0018(24),
    Game_Enter(25),
    Player_Move(26),
    NPC_Move(27),
    Player_Attack(28),
    Player_Jump(29),
    Player_Skill_Attack(30),
    Unk0x001F(31),
    Unk0x0020(32),
    Unk0x0021(33),
    Unk0x0022(34),
    Unk0x0023(35),
    Unk0x0024(36),
    Unk0x0025(37),
    Unk0x0026(38),
    Unk0x0027(39),
    Player_Appear(40),
    Player_Disappear(41),
    Npc_Appear(42),
    Npc_Disappear(43),
    Item_Spawn(44),
    Item_Disappear(45),
    Damage(46),
    Player_Update2(47),
    Player_Emote(48),
    Item_Pickup(49),
    Player_EXP_Level(50),
    Player_Death(51),
    Player_Revive(52),
    Item_Move(53),
    Item_Equip(54),
    Item_Unequip(55),
    Item_Drop(56),
    Unk0x0039(57),
    Player_UpdateHPMP(58),
    Player_Update1(59),
    Map_Transfer(60),
    Player_Chat(61),
    LevelUp_Effect(62),
    Inventory_Update(63),
    Unk0x0040(64),
    NPC_Buy_Item(65),
    NPC_Sell_Item(66),
    Unk0x0043(67),
    Equip_Arrows(68),
    Unequip_Arrows(69),
    Unk0x0046(70),
    Skill_Effect(71),
    Skill_Point(72),
    Player_Restart(73),
    Game_Options(74),
    Unk0x004B(75),
    Unk0x004C(76),
    Unk0x004D(77),
    Unk0x004E(78),
    Unk0x004F(79),
    Unk0x0050(80),
    End_Transaction(81),
    Unk0x0052(82),
    Unk0x0053(83),
    Npc_Talk(84),
    Quest_Receive(85),
    Quest_Complete(86),
    QuestList(87),
    Unk0x0058(88),
    Unk0x0059(89),
    Unk0x005A(90),
    Unk0x005B(91),
    Update_Inventory_Slot(92),
    Unk0x005D(93),
    Dev_Command(94),
    Unk0x005F(95),
    Unk0x0060(96),
    Unk0x0061(97),
    Unk0x0062(98),
    Unk0x0063(99),
    Quest_Data(100), // v2 = (__int8)(*pPacket); qmemcpy(this + 33, pPacket + 1, 4 * (4 * (unsigned int)v2 >> 2));
    Warehouse_Add_Item(101),
    Warehouse_Remove_Item(102),
    Unk0x0067(103),
    Unk0x0068(104),
    Unk0x0069(105),
    Ping(106),
    Admin_Chat(107),
    Unk0x006C(108),
    Unk0x006D(109),
    Unk0x006E(110),
    Unk0x006F(111),
    Pet_Move(112),
    Unk0x0071(113),
    Pet_Jump(114),
    Unk0x0073(115),
    Pet_Spawn(116),
    Pet_Remove(117),
    Unk0x0076(118),
    Unk0x0077(119),
    Unk0x0078(120),
    Unk0x0079(121),
    Unk0x007A(122),
    Unk0x007B(123),
    Unk0x007C(124),
    Pet_List(125),
    Party_Request(126),
    Party_Request_Response(127),
    Party_List(128),
    Unk0x0081(129),
    Unk0x0082(130),
    Unk0x0083(131),
    Unk0x0084(132),
    Unk0x0085(133),
    Unk0x0086(134),
    Unk0x0087(135),
    Unk0x0088(136),
    Unk0x0089(137),
    Unk0x008A(138),
    Player_Whisper(139),
    Unk0x008C(140),
    Unk0x008D(141),
    Job_Change_IDRequest(142),
    Job_Change_ID(143),
    Unk0x0090(144),
    Unk0x0091(145),
    Unk0x0092(146),
    Unk0x0093(147),
    Unk0x0094(148),
    Unk0x0095(149),
    Unk0x0096(150),
    Unk0x0097(151),
    Player_Quit(152),
    Unk0x0099(153),
    Unk0x009A(154),
    Unk0x009B(155),
    Unk0x009C(156),
    Unk0x009D(157),
    Unk0x009E(158),
    Unk0x009F(159),
    Unk0x00A0(160),
    Unk0x00A1(161),
    Unk0x00A2(162),
    Unk0x00A3(163),
    Channel_Change(164),
    Buddy_Request(165),
    Buddy_Response(166),
    Buddy_List(167),
    Buddy_List_Update(168),
    Buddy_Delete(169),
    Unk0x00AA(170),
    Unk0x00AB(171),
    Talk_To(172),
    Unk0x00AD(173),
    Unk0x00AE(174),
    Party_Update_HP_MP(175),
    Unk0x00B0(176),
    Unk0x00B1(177),
    Unk0x00B2(178),
    Unk0x00B3(179),
    Unk0x00B4(180),
    Unk0x00B5(181),
    Unk0x00B6(182),
    Unk0x00B7(183),
    Unk0x00B8(184),
    Unk0x00B9(185),
    Unk0x00BA(186),
    Player_Dash(187),
    Unk0x00BC(188),
    Unk0x00BD(189),
    Unk0x00BE(190),
    Unk0x00BF(191),
    Unk0x00C0(192),
    Unk0x00C1(193),
    Unk0x00C2(194),
    ShowStores(195),
    Unk0x00C4(196),
    Unk0x00C5(197),
    Unk0x00C6(198),
    Notice(199),
    Unk0x00C8(200),
    Guild_Name_Check(201),
    Guild_Create(202),
    Guild_Data(203),
    Unk0x00CC(204),
    Unk0x00CD(205),
    Unk0x00CE(206),
    Unk0x00CF(207),
    Unk0x00D0(208),
    Unk0x00D1(209),
    Unk0x00D2(210),
    Unk0x00D3(211),
    Unk0x00D4(212),
    Unk0x00D5(213),
    Unk0x00D6(214),
    Unk0x00D7(215),
    Unk0x00D8(216),
    Unk0x00D9(217),
    Unk0x00DA(218),
    Unk0x00DB(219),
    Unk0x00DC(220),
    Unk0x00DD(221),
    SetGuild(222),
    Guild_Member_Status(223),
    Unk0x00E0(224),
    SetGuild2(225),
    Unk0x00E2(226),
    Unk0x00E3(227),
    SetGuildToOthers(228),
    Unk0x00E5(229),
    Unk0x00E6(230),
    Unk0x00E7(231),
    Unk0x00E8(232),
    Unk0x00E9(233),
    Unk0x00EA(234),
    Unk0x00EB(235),
    Unk0x00EC(236),
    Unk0x00ED(237),
    Unk0x00EE(238),
    Party_Change_Leader_Request(239),
    Party_Change_Leader(240),
    Unk0x00F1(241),
    Warehouse_Move_Item(242),
    Unk0x00F3(243),
    Unk0x00F4(244),
    Unk0x00F5(245),
    Unk0x00F6(246),
    Unk0x00F7(247),
    Unk0x00F8(248),
    Attendance(249),
    Unk0x00FA(250),
    Unk0x00FB(251),
    Use_Warper(252),
    Unk0x00FD(253),
    Unk0x00FE(254),
    Cash_BuyItem(255),
    Unk0x0100(256),
    AlertGift_Box(257),
    Gift_Box(258),
    Receive_Gift(259),
    Unk0x0104(260),
    Cash_Equip(261),
    Cash_UnEquip(262),
    Cash_Inventory(263),
    Unk0x0108(264),
    Unk0x0109(265),
    Unk0x010A(266),
    Unk0x010B(267),
    Cash_ItemUse(268),
    Unk0x010D(269),
    Purple_Announcement(270),
    Unk0x010F(271),
    Unk0x0110(272),
    Cash_Move(273),
    Cashshop_Enter(274),
    Unk0x0113(275),
    Unk0x0114(276),
    Unk0x0115(277),
    Unk0x0116(278),
    Unk0x0117(279),
    Unk0x0118(280),
    Unk0x0119(281),
    Unk0x011A(282),
    Unk0x011B(283),
    Unk0x011C(284),
    Unk0x011D(285),
    Unk0x011E(286),
    Unk0x011F(287),
    Unk0x0120(288),
    Unk0x0121(289),
    Unk0x0122(290),
    Unk0x0123(291),
    Unk0x0124(292),
    Unk0x0125(293),
    Unk0x0126(294),
    Unk0x0127(295),
    Unk0x0128(296),
    Unk0x0129(297),
    Unk0x012A(298),
    Unk0x012B(299),
    Unk0x012C(300),
    Unk0x012D(301),
    Unk0x012E(302),
    Unk0x012F(303),
    Unk0x0130(304),
    Unk0x0131(305),
    Unk0x0132(306),
    Unk0x0133(307),
    Unk0x0134(308),
    Unk0x0135(309),
    Unk0x0136(310),
    Unk0x0137(311),
    Unk0x0138(312),
    Update_Inventory(313),
    Unk0x013A(314),
    CashShop_Info(315),
    Unk0x013C(316),
    Unk0x013D(317),
    Attraction(318),
    Player_RightClick(319),
    Give_Fame(320),
    Update_Fame(321),
    ReceiveFame(322),
    Unk0x0143(323),
    Unk0x0144(324),
    Unk0x0145(325),
    Unk0x0146(326),
    Unk0x0147(327),
    Unk0x0148(328),
    Unk0x0149(329),
    Unk0x014A(330),
    OpCode0x014B(331),
    Unk0x014C(332),
    Unk0x014D(333),
    Unk0x014E(334),
    Unk0x014F(335),
    Unk0x0150(336),
    Unk0x0151(337),
    Unk0x0152(338),
    Unk0x0153(339),
    Unk0x0154(340),
    Unk0x0155(341),
    Unk0x0156(342),
    Start_Mining(343),
    Stop_Mining(344),
    Unk0x0159(345),
    Unk0x015A(346),
    Unk0x015B(347),
    Unk0x015C(348),
    WonderGate_Appear(349),
    WonderGate_Disappear(350),
    WonderGate_Use(351),
    Unk0x0160(352),
    Unk0x0161(353),
    Unk0x0162(354),
    Unk0x0163(355),
    Unk0x0164(356),
    Unk0x0165(357),
    Unk0x0166(358),
    Unk0x0167(359),
    Unk0x0168(360),
    Mail_List(361),
    Mail_Read(362),
    Mail_Delete(363),
    Mail_Write(364),
    Mail_New(365),
    Unk0x016E(366),
    Unk0x016F(367),
    Unk0x0170(368),
    Unk0x0171(369),
    Unk0x0172(370),
    Unk0x0173(371),
    Unk0x0174(372),
    Unk0x0175(373),
    Unk0x0176(374),
    Unk0x0177(375),
    Unk0x0178(376),
    Party_Option_Change(377),
    Unk0x017A(378),
    Unk0x017B(379),
    Unk0x017C(380),
    Unk0x017D(381),
    Unk0x017E(382),
    Unk0x017F(383),
    Player_ViewInfo(384),
    Unk0x0181(385),
    Unk0x0182(386),
    Unk0x0183(387),
    Unk0x0184(388),
    Unk0x0185(389),
    Unk0x0186(390),
    Unk0x0187(391),
    Unk0x0188(392),
    Unk0x0189(393),
    Unk0x018A(394),
    Unk0x018B(395),
    Unk0x018C(396),
    Unk0x018D(397),
    Unk0x018E(398),
    Unk0x018F(399),
    Unk0x0190(400),
    Unk0x0191(401),
    Unk0x0192(402),
    Unk0x0193(403),
    Unk0x0194(404),
    Unk0x0195(405),
    Unk0x0196(406),
    Unk0x0197(407),
    PVPPlayerLevel(408),
    Unk0x0199(409),
    Keyboard(410),
    PvP_Rooms(411),
    PvP_CreateRoom(412),
    PVP_JoinRoom(413),
    Unk0x019E(414),
    PvP_RoomInfo(415),
    Unk0x01A0(416),
    Unk0x01A1(417),
    Unk0x01A2(418),
    Unk0x01A3(419),
    RequestPVPLevel(420),
    Unk0x01A5(421),
    Unk0x01A6(422),
    Unk0x01A7(423),
    Unk0x01A8(424),
    Character_Info_To_Server(425),
    Unk0x01AA(426),
    Unk0x01AB(427),
    Unk0x01AC(428),
    Unk0x01AD(429),
    Unk0x01AE(430),
    Unk0x01AF(431),
    Unk0x01B0(432),
    Unk0x01B1(433),
    Unk0x01B2(434),
    Unk0x01B3(435),
    Unk0x01B4(436),
    Unk0x01B5(437),
    Unk0x01B6(438),
    Unk0x01B7(439),
    Unk0x01B8(440),
    Unk0x01B9(441),
    Unk0x01BA(442),
    Unk0x01BB(443),
    Unk0x01BC(444),
    Unk0x01BD(445),
    Unk0x01BE(446),
    Unk0x01BF(447),
    Unk0x01C0(448),
    Unk0x01C1(449),
    Unk0x01C2(450),
    Unk0x01C3(451),
    Unk0x01C4(452),
    Unk0x01C5(453),
    Unk0x01C6(454),
    Unk0x01C7(455),
    Unk0x01C8(456),
    Unk0x01C9(457),
    Unk0x01CA(458),
    Unk0x01CB(459),
    Unk0x01CC(460),
    Unk0x01CD(461),
    Unk0x01CE(462),
    Unk0x01CF(463),
    Unk0x01D0(464),
    Unk0x01D1(465),
    Unk0x01D2(466),
    Unk0x01D3(467),
    Unk0x01D4(468),
    Unk0x01D5(469),
    Unk0x01D6(470),
    Unk0x01D7(471),
    Unk0x01D8(472),
    Unk0x01D9(473),
    Unk0x01DA(474),
    Unk0x01DB(475),
    Unk0x01DC(476),
    Unk0x01DD(477),
    Unk0x01DE(478),
    Unk0x01DF(479),
    Unk0x01E0(480),
    Unk0x01E1(481),
    Unk0x01E2(482),
    Unk0x01E3(483),
    Unk0x01E4(484),
    Unk0x01E5(485),
    Unk0x01E6(486),
    Unk0x01E7(487),
    Unk0x01E8(488),
    Unk0x01E9(489),
    Unk0x01EA(490),
    Unk0x01EB(491),
    Unk0x01EC(492),
    Unk0x01ED(493),
    Unk0x01EE(494),
    Unk0x01EF(495),
    Unk0x01F0(496),
    Unk0x01F1(497),
    Unk0x01F2(498),
    Unk0x01F3(499),
    Unk0x01F4(500);
    public final int Id;
    public Class<? extends PacketRequest> handler;
    private static final PacketOperations[] VALUES = values();

    static {
        Login_Info.handler = LoginRequest.class;
        Channel_Select.handler = ChannelSelectRequest.class;
        Channel_Change.handler = ChannelChangeRequest.class;
        Game_Enter.handler = GameEnterRequest.class;

        Character_Name_Check.handler = PlayerNameCheckRequest.class;
        Character_Create.handler = PlayerCreateRequest.class;
        Character_Delete.handler = PlayerDeleteRequest.class;
        Character_Select.handler = PlayerSelectRequest.class;

        Player_Move.handler = PlayerMoveRequest.class;
        Player_Jump.handler = PlayerJumpRequest.class;
        Player_Dash.handler = PlayerDashRequest.class;
        Player_Restart.handler = PlayerRestartRequest.class;
        Player_Reconnect.handler = PlayerReconnectRequest.class;
        Player_Emote.handler = PlayerEmoteRequest.class;
        Player_RightClick.handler = PlayerRightClickRequest.class;
        Player_ViewInfo.handler = PlayerViewInfoRequest.class;
        Player_Chat.handler = PlayerChatRequest.class;
        Player_Quit.handler = PlayerQuitRequest.class;

        Item_Unequip.handler = PlayerUnequipRequest.class;

        Quest_Receive.handler = PlayerQuestReceiveRequest.class;
        Quest_Complete.handler = PlayerQuestCompleteRequest.class;

        Map_Transfer.handler = PlayerMapTransferRequest.class;
        Attraction.handler = PlayerAttractionRequest.class;

        Npc_Talk.handler = PlayerNpcTalkRequest.class;
    }

    PacketOperations(int Id) {
        this.Id = Id;
    }

    public static Class<? extends PacketRequest> handlerOf(int header) {
        return VALUES[header].handler;
    }
}
