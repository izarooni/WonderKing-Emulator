package com.izarooni.wkem.server.world.life;

import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.util.Disposable;

/**
 * @author izarooni
 */
public abstract class Entity implements Disposable {

    public abstract void encode(EndianWriter w);
}
