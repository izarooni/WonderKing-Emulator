package com.izarooni.wkem.life.meta;

/**
 * @author izarooni
 */
public enum Element {
    Fire(Element.Water), Water(Element.Fire), Dark(Element.Holy), Holy(Element.Dark);
    final Element weakness;

    Element(Element weakness) {
        this.weakness = weakness;
    }
}
