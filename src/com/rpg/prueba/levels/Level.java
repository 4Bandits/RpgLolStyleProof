package com.rpg.prueba.levels;

import java.util.HashMap;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.rpg.prueba.sprites.ChampionSprite;
import com.rpg.prueba.sprites.StationarySprite;



public class Level {

    protected TiledMap map;
    protected Array<ChampionSprite> sprites;
    protected Array<StationarySprite> stationarySprites;
    
    protected float red;
    protected float green;
    protected float blue;

    public Level() {
        sprites = new Array<ChampionSprite>();
        stationarySprites = new Array<StationarySprite>();
      
    }

    public Array<StationarySprite> getStationarySprites() {
        return stationarySprites;
    }

    public TiledMap getMap() {
        return map;
    }

    public Array<ChampionSprite> getSprites() {
        return sprites;
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    
}
