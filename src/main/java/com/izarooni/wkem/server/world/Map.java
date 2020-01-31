package com.izarooni.wkem.server.world;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.io.MapFactory;
import com.izarooni.wkem.io.meta.TemplateMap;
import com.izarooni.wkem.packet.magic.GamePacketCreator;
import com.izarooni.wkem.server.world.life.Entity;
import com.izarooni.wkem.server.world.life.Player;
import com.izarooni.wkem.util.PacketAnnouncer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * @author izarooni
 */
public class Map implements PacketAnnouncer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Map.class);

    private final int id;
    private final TemplateMap template;
    // maybe convert to an EnumMap for entity types in the future
    private final ConcurrentHashMap<Integer, Player> players;

    public Map(int id) {
        this.id = id;

        template = MapFactory.get(id);
        players = new ConcurrentHashMap<>(10);
    }

    @Override
    public Stream<User> getUsers() {
        return players.values().stream().map(Player::getUser);
    }

    public int getId() {
        return id;
    }

    public TemplateMap getTemplate() {
        return template;
    }

    public ConcurrentHashMap<Integer, Player> getPlayers() {
        return players;
    }

    public void addEntity(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            User user = player.getUser();

            Map oldMap = player.getMap();
            oldMap.removeEntity(player);
            player.setMap(this);
            player.setMapId(getId());

            sendPacket(GamePacketCreator.getPlayerAppear(player));
            user.sendPacket(GamePacketCreator.getPlayerMapTransfer(player, this));
            user.sendPacket(GamePacketCreator.getGameEnter());

            players.put(player.getId(), player);
        }
    }

    public void removeEntity(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            players.remove(player.getId());
            player.setMap(null);

            sendPacket(GamePacketCreator.getPlayerDisappear(player));
        }
    }
}
