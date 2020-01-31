package com.izarooni.wkem.server.world;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.io.MapFactory;
import com.izarooni.wkem.io.meta.TemplateMap;
import com.izarooni.wkem.io.meta.TemplateMapPortal;
import com.izarooni.wkem.packet.magic.GamePacketCreator;
import com.izarooni.wkem.server.world.life.Entity;
import com.izarooni.wkem.server.world.life.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author izarooni
 */
public class Map {

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
            players.put(player.getId(), player);

            user.sendPacket(GamePacketCreator.getPlayersInMap(this));
            user.sendPacket(GamePacketCreator.getPlayerMapTransfer(player, this));
            user.sendPacket(GamePacketCreator.getGameEnter());
            LOGGER.info("'{}' moved to map '{}'", player.getUsername(), template.toString());
        }
    }

    public void removeEntity(Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            players.remove(player.getId());
            player.setMap(null);
            LOGGER.info("'{}' left map '{}'", player.getUsername(), template.toString());
        }
    }
}
