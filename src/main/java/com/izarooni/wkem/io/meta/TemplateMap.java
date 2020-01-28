package com.izarooni.wkem.io.meta;

import java.util.ArrayList;

/**
 * @author izarooni
 */
public class TemplateMap {

    public int id;
    public String name;
    public long checksum;
    public long flag1, flag2, flag3;
    public int mapWidth, mapHeight;
    public int bgCount;
    public int tileCount1, tileCount2;
    public int animCount, animCount2;
    public int footholdCount;
    public ArrayList<TemplateMapBackground> backgroundTiles;
    public ArrayList<TemplateMapTile> tiles, tiles2;
    public ArrayList<TemplateMapAnimation> animations, animations2;
    public ArrayList<TemplateMapFoothold> footholds;
    public ArrayList<TemplateSpawnPoint> spawnPoints;
    public ArrayList<TemplateMapPortal> portals;

    @Override
    public String toString() {
        return String.format("TemplateMap{id=%d, name='%s'}", id, name);
    }
}
