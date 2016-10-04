package com.rpg.prueba.sprites;

import java.util.Comparator;

public class SpriteUpComparator implements Comparator<ChampionSprite> {
    @Override
    public int compare(ChampionSprite s1, ChampionSprite s2) {
        return s2.compareTo(s1);
    }
}
