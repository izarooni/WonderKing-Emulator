package com.izarooni.wkem.io;

import com.izarooni.wkem.io.meta.*;
import com.izarooni.wkem.packet.accessor.EndianReader;
import com.izarooni.wkem.util.Vector2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author izarooni
 */
public class MapFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapFactory.class);
    static final HashMap<Integer, TemplateMap> templateMaps = new HashMap<>(500);

    private MapFactory() {
    }

    public static TemplateMap get(int mapID) {
        return templateMaps.get(mapID);
    }

    public static void createCache() throws IOException {
        loadMapNames();
        for (Map.Entry<Integer, TemplateMap> e : templateMaps.entrySet()) {
            TemplateMap template = e.getValue();
            try {
                loadMapData(template);
            } catch (Exception ex) {
                LOGGER.error("Failed to load map '{}'", template.toString(), ex);
            }
        }
    }

    public static void loadMapNames() throws IOException {
        try (FileInputStream fin = DataReader.openFile(new File("value", "basemapdata.dat"))) {
            EndianReader reader = new EndianReader(fin.readAllBytes());
            reader.skip(5);
            int count = reader.readInt();
            reader.skip(4);
            for (int i = 0; i < count; i++) {
                TemplateMap template = new TemplateMap();
                // map 999 has only 32 chars for a name
                template.id = reader.readInt();
                template.name = reader.readAsciiString(Math.min(reader.available(), 36)).trim();
                templateMaps.put(template.id, template);
            }
        }
    }

    public static void loadMapData(TemplateMap template) throws IOException {
        File file = new File("value", String.format("MopRejenData_%d.dat", template.id));
        if (file.exists()) {
            try (FileInputStream fin = DataReader.openFile(file)) {
                EndianReader reader = new EndianReader(fin.readAllBytes());
                decodeRejenData(template, reader);
            }
        }

        file = new File("value", String.format("mapdata_%d.dat", template.id));
        if (!file.exists()) {
            LOGGER.warn("map '{}' exists in names but no data is found", template.toString());
            templateMaps.remove(template.id);
            return;
        }
        try (FileInputStream fin = DataReader.openFile(file)) {
            EndianReader reader = new EndianReader(fin.readAllBytes());
            //region header
            template.checksum = reader.readUnsignedInt();
            template.flag1 = reader.readUnsignedInt();
            long mapID = reader.readUnsignedInt();
            if (mapID != template.id)
                throw new RuntimeException(String.format("mapID mismatch... Required %d but found %d", template.id, mapID));
            template.flag2 = reader.readUnsignedInt();
            template.flag3 = reader.readUnsignedInt();
            template.mapWidth = reader.readInt();
            template.mapHeight = reader.readInt();
            reader.skip(4 * 4);
            template.bgCount = reader.readInt();
            template.tileCount1 = reader.readInt();
            template.animCount = reader.readInt();
            template.footholdCount = reader.readInt();
            template.tileCount2 = reader.readInt();
            template.animCount2 = reader.readInt();
            //endregion
            //region bg tiles
            template.backgroundTiles = new ArrayList<>(template.bgCount);
            for (int i = 0; i < template.bgCount; i++) {
                TemplateMapBackground bg = new TemplateMapBackground();
                bg.originLocation = new Vector2D(reader.readInt(), reader.readInt());
                bg.tileCount = reader.readInt();
                bg.originX = new int[10];
                insertInt(reader, bg.originX);
                bg.originY = new int[10];
                insertInt(reader, bg.originY);
                bg.width = new int[10];
                insertInt(reader, bg.width);
                bg.height = new int[10];
                insertInt(reader, bg.height);
                bg.tilesetX = new int[10];
                insertInt(reader, bg.tilesetX);
                bg.tilesetY = new int[10];
                insertInt(reader, bg.tilesetY);
                bg.fileID = new int[10];
                insertInt(reader, bg.fileID);
                template.backgroundTiles.add(bg);
            }
            //endregion
            //region tiles
            template.tiles = new ArrayList<>(template.tileCount1);
            for (int i = 0; i < template.tileCount1; i++) {
                template.tiles.add(decodeTiles(reader));
            }
            //endregion
            //region animations
            template.animations = new ArrayList<>(template.animCount);
            for (int i = 0; i < template.animCount; i++) {
                template.animations.add(decodeAnimations(reader));
            }
            //endregion
            //region footholds
            template.footholds = new ArrayList<>(template.footholdCount);
            for (int i = 0; i < template.footholdCount; i++) {
                TemplateMapFoothold fh = new TemplateMapFoothold();
                fh.originLocation = new Vector2D(reader.readInt(), reader.readInt());
                fh.incX = reader.readInt();
                fh.incY = reader.readInt();
                fh.direction = reader.readInt();
                fh.empty = new long[15];
                for (int j = 0; j < fh.empty.length; j++) {
                    fh.empty[j] = reader.readUnsignedInt();
                }
                fh.area = new Rectangle(fh.originLocation.getX(), fh.originLocation.getY(), fh.incX, fh.incY);
                template.footholds.add(fh);
            }
            //endregion
            //region tiles2
            template.tiles2 = new ArrayList<>(template.tileCount2);
            for (int i = 0; i < template.tileCount2; i++) {
                template.tiles2.add(decodeTiles(reader));
            }
            //endregion
            //region animations2
            template.animations2 = new ArrayList<>(template.animCount2);
            for (int i = 0; i < template.animCount2; i++) {
                template.animations2.add(decodeAnimations(reader));
            }
            //endregion
        }
        for (TemplateMapFoothold fh : template.footholds) {
            if (fh.direction == 3) {
                fh.area = new Rectangle(fh.originLocation.getX(), fh.originLocation.getY() - 1 - fh.incY, fh.incX, fh.incY);
            }
            if (template.spawnPoints != null) {
                for (TemplateSpawnPoint sp : template.spawnPoints) {
                    if (sp.foothold == null) {
                        if (fh.area.intersects(sp.area)) {
                            sp.area = sp.area.intersection(fh.area);
                            sp.foothold = fh;
                        }
                    }
                }
            }
        }
    }

    private static void decodeRejenData(TemplateMap template, EndianReader reader) {
        reader.readInt();
        reader.readByte();
        short mapID = reader.readShort();
        if (mapID != template.id)
            throw new RuntimeException(String.format("mapID mismatch... Required %d but found %d", template.id, mapID));
        int spawnCount = reader.readShort();
        template.spawnPoints = new ArrayList<>(spawnCount);
        for (int i = 0; i < spawnCount; i++) {
            TemplateSpawnPoint sp = new TemplateSpawnPoint();
            int x = reader.readInt(), y = reader.readInt();
            int width = reader.readInt(), height = reader.readInt();
            sp.area = new Rectangle(x, y, width, height);
            sp.id = reader.readInt();
            sp.entityType = reader.readInt();
            template.spawnPoints.add(sp);
        }
        int portalCount = reader.readShort();
        template.portals = new ArrayList<>(portalCount);
        for (int i = 0; i < portalCount; i++) {
            TemplateMapPortal p = new TemplateMapPortal();
            p.location = new Vector2D(reader.readInt(), reader.readInt());
            p.width = reader.readInt();
            p.height = reader.readInt();
            p.flag = reader.readShort();
            p.unk1 = reader.readShort();
            p.unk2 = reader.readLong();
            p.unk3 = reader.readInt();
            p.destinationID = reader.readShort();
            reader.skip(54);
            template.portals.add(p);
        }
    }

    private static TemplateMapAnimation decodeAnimations(EndianReader reader) {
        TemplateMapAnimation a = new TemplateMapAnimation();
        a.unk1 = reader.readInt();
        a.originLocation = new Vector2D(reader.readInt(), reader.readInt());
        a.width = reader.readUnsignedInt();
        a.height = reader.readUnsignedInt();
        a.frameCount = reader.readUnsignedInt();
        a.unk2 = reader.readUnsignedInt();
        a.anX = new int[40];
        insertInt(reader, a.anX);
        a.anY = new int[40];
        insertInt(reader, a.anY);
        a.delay = new int[40];
        insertInt(reader, a.delay);
        a.fileID = new int[40];
        insertInt(reader, a.fileID);
        return a;
    }

    private static TemplateMapTile decodeTiles(EndianReader reader) {
        TemplateMapTile t = new TemplateMapTile();
        t.originLocation = new Vector2D(reader.readInt(), reader.readInt());
        t.width = reader.readInt();
        t.height = reader.readInt();
        t.tilesetX = reader.readUnsignedInt();
        t.tilesetY = reader.readUnsignedInt();
        t.format = reader.readInt();
        t.fileID = reader.readUnsignedInt();
        return t;
    }

    private static void insertInt(EndianReader reader, int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = reader.readInt();
        }
    }
}
