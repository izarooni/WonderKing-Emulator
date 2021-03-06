package com.izarooni.wkem.io.meta;

import com.izarooni.wkem.util.Vector2D;

import java.awt.*;

/**
 * @author izarooni
 */
public class TemplateMapFoothold {

    public Vector2D originLocation;
    public int incX, incY;

    /**
     * <ol start=1>
     *     <li>bl_to_tr</li>
     *     <li>tl_to_br</li>
     *     <li>tl_to_tr</li>
     * </ol>
     */
    public int direction;
    public long[] empty;
    public Rectangle area;

    @Override
    public String toString() {
        return String.format("TemplateMapFoothold{originLocation=%s, incX=%d, incY=%d, direction=%d, area=%s}", originLocation, incX, incY, direction, area);
    }
}
