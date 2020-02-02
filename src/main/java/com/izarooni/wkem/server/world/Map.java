package com.izarooni.wkem.server.world;

import com.izarooni.wkem.client.User;
import com.izarooni.wkem.io.MapFactory;
import com.izarooni.wkem.io.meta.TemplateMap;
import com.izarooni.wkem.io.meta.TemplateMapPortal;
import com.izarooni.wkem.io.meta.TemplateSpawnPoint;
import com.izarooni.wkem.packet.magic.GamePacketCreator;
import com.izarooni.wkem.server.world.life.Entity;
import com.izarooni.wkem.server.world.life.Npc;
import com.izarooni.wkem.server.world.life.Player;
import com.izarooni.wkem.util.Disposable;
import com.izarooni.wkem.util.PacketAnnouncer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author izarooni
 */
public class Map implements PacketAnnouncer, Disposable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Map.class);

    private final int id;
    private final TemplateMap template;
    private final AtomicInteger UID;
    // maybe convert to an EnumMap for entity types in the future
    private final ConcurrentHashMap<Integer, Player> players;
    private ConcurrentHashMap<Integer, Npc> npcs;

    public Map(int id) {
        this.id = id;

        template = MapFactory.get(id);
        UID = new AtomicInteger(1);
        players = new ConcurrentHashMap<>(10);
        if (template.spawnPoints != null) {
            npcs = new ConcurrentHashMap<>(template.spawnPoints.size());
            for (TemplateSpawnPoint sp : template.spawnPoints) {
                addEntity(new Npc(sp));
            }
        }
    }

    @Override
    public Stream<User> getUsers() {
        return players.values().stream().map(Player::getUser);
    }

    @Override
    public void dispose() {
        players.clear();
        npcs.clear();
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

    public Optional<Player> findPlayer(Predicate<Player> p) {
        return players.values().stream().filter(p).findFirst();
    }

    public Optional<TemplateMapPortal> findPortal(Predicate<TemplateMapPortal> p) {
        return template.portals.stream().filter(p).findFirst();
    }

    public void addEntity(Entity entity) {
        entity.setObjectID(UID.getAndIncrement());
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

            if (npcs != null) {
                for (Npc npc : npcs.values()) {
                    user.sendPacket(GamePacketCreator.getNpcAppear(npc));
                }
            }

            players.put(player.getId(), player);
        } else if (entity instanceof Npc) {
            npcs.put(entity.getObjectID(), (Npc) entity);
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
