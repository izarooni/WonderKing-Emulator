package com.izarooni.wkem.server.world.life;

import com.izarooni.wkem.io.meta.TemplateSpawnPoint;
import com.izarooni.wkem.packet.accessor.EndianWriter;
import com.izarooni.wkem.server.world.Physics;
import com.izarooni.wkem.server.world.life.meta.Vector2D;

/**
 * @author izarooni
 */
public class Npc extends Entity {

    private final TemplateSpawnPoint template;

    public Npc(TemplateSpawnPoint template) {
        this.template = template;

        setLocation(new Vector2D(template.area.x, template.area.y));
    }

    @Override // [0004478D0]
    public void encode(EndianWriter w) {
        // 69 bytes
        w.writeShort(template.unk1);
        w.write(0);
        w.writeShort(getObjectID());
        w.writeShort(template.id);
        w.writeInt(0);
        w.writeShort(getLocation().getX());
        w.writeShort(getLocation().getY());
        w.writeShort(getHp());
        w.writeShort(getMp());
        w.writeShort((int) Physics.XVelocity); // 23
        w.writeShort((int) Physics.YVelocity); // 27
        w.write(0);
        w.write(42); // 29
        w.skip(40);
    }

    @Override
    public void dispose() {

    }
}
