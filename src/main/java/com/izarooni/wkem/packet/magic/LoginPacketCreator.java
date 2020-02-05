package com.izarooni.wkem.packet.magic;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.server.Server;
import com.izarooni.wkem.server.world.Channel;
import com.izarooni.wkem.life.Player;
import com.izarooni.wkem.service.Backbone;

/**
 * @author izarooni
 */
public enum LoginPacketCreator {

    LoginResponse_Ok(0),
    LoginResponse_AccountNotExist(1),
    LoginResponse_IncorrectPassword(2),
    LoginResponse_Error(3),
    LoginResponse_DuplicateConnection(4),
    LoginResponse_Suspended(5),
    LoginResponse_PleaseRegister(6),
    LoginResponse_Suspended2(7),
    LoginResponse_EmailVerification(8),
    LoginResponse_NotCBTAccount(9),
    LoginResponse_ServerInternalError(10),
    LoginResponse_PaymentIssue(11),
    LoginResponse_UserDeleted(12),
    LoginResponse_NotMoreTries(13),
    NameCheckResponse_Ok(0),
    NameCheckResponse_AlreadyExists(1),
    NameCheckResponse_Nothing(2),
    NameCheckResponse_Error(3),
    NameCheckResponse_Incorrect(4),
    ;
    public final int Id;

    LoginPacketCreator(int Id) {
        this.Id = Id;
    }

    public static EndianWriter getLoginResponse(int r) {
        if (r > LoginResponse_NotMoreTries.Id) throw new IllegalArgumentException("invalid response");
        EndianWriter w = new EndianWriter(3);
        w.writeShort(PacketOperations.Login_Response.Id);
        w.write(r);
        return w;
    }

    public static EndianWriter getLoginResponse(LoginPacketCreator r) {
        return getLoginResponse(r.Id);
    }

    // [004D4550]
    public static EndianWriter getChannelList() {
        EndianWriter w = getLoginResponse(LoginResponse_Ok);
        w.write(0);
        w.write(1); // admin

        int channelCount = Backbone.getServers().stream().mapToInt(s -> s.getChannels().size()).sum();
        w.writeShort(channelCount);
        for (Server server : Backbone.getServers()) {
            for (Channel channel : server.getChannels()) {
                w.writeInt(server.getId());
                w.write(channel.getId());
            }
        }
        return w;
    }

    // Referenced from [004C24F0]
    public static EndianWriter getPlayerList(User user) {
        Channel channel = user.getChannel();

        EndianWriter w = new EndianWriter();
        w.writeShort(PacketOperations.Channel_Select.Id);
        w.write(0);
        w.writeAsciiString(channel.getAddress(), 16);
        w.writeShort(channel.getPort());
        int playerCount = 0;
        for (Player player : user.getPlayers()) {
            if (player != null) {
                playerCount++;
            }
        }
        w.write(playerCount);
        for (Player player : user.getPlayers()) {
            if (player != null) {
                player.encodeBasic(w);
            }
        }
        return w;
    }

    public static EndianWriter getNameCheckResponse(LoginPacketCreator r) {
        return getNameCheckResponse(r.Id);
    }

    public static EndianWriter getNameCheckResponse(int r) {
        if (r > NameCheckResponse_Incorrect.Id) throw new IllegalArgumentException("invalid response");
        EndianWriter w = new EndianWriter();
        w.writeShort(PacketOperations.Character_Name_Check.Id);
        w.write(r);
        return w;
    }

    public static EndianWriter getDeletePlayer(byte loginPosition) {
        EndianWriter w = new EndianWriter(3);
        w.writeShort(PacketOperations.Character_Delete.Id);
        w.write(loginPosition);
        return w;
    }

    public static EndianWriter getSelectPlayer(User user) {
        EndianWriter w = new EndianWriter();
        w.writeShort(PacketOperations.Character_Select.Id);
        w.write(0);
        w.writeAsciiString(user.getPassword(), 32);
        w.write(0);
        w.write(new byte[22]);
        return w;
    }
}
