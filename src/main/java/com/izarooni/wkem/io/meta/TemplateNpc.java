package com.izarooni.wkem.io.meta;

/**
 * @author izarooni
 */
public class TemplateNpc {

    public String name;
    public String text;
    public int unk1;
    public int id;
    public int level;
    public int hp, mp;
    public int exp;
    public float speed;
    public int minAtk, maxAtk;
    public int[] drops;

    @Override
    public String toString() {
        return String.format("TemplateNpc{name='%s', text='%s', id=%d}", name, text, id);
    }
}
