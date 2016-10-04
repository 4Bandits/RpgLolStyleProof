package com.rpg.prueba.sprites;

import java.util.Comparator;

public class SpriteDownComparator implements Comparator<ChampionSprite> {
    @Override
    public int compare(ChampionSprite s1, ChampionSprite s2) {
        return s1.compareTo(s2);
    }
}
