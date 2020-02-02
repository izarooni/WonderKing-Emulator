package com.izarooni.wkem.io.meta;

import java.awt.*;

/**
 * @author izarooni
 */
public class TemplateSpawnPoint {

    public Rectangle area;
    public int id;
    public int unk1;
    public int y;
    public TemplateMapFoothold foothold;

    @Override
    public String toString() {
        return String.format("TemplateSpawnPoint{area=%s, id=%d, unk1=%d, y=%d}", area, id, unk1, y);
    }
}
