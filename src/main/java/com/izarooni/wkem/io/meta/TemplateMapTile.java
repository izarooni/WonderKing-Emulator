package com.izarooni.wkem.io.meta;

import com.izarooni.wkem.server.world.life.meta.Vector2D;

/**
 * @author izarooni
 */
public class TemplateMapTile {

    public Vector2D originLocation;
    public int width, height;
    public long tilesetX, tilesetY;

    /**
     * <ol start=1>
     *     <li>_1b</li>
     *     <li>_8b</li>
     * </ol>
     */
    public int format;
    public long fileID;
}
