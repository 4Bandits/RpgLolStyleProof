package com.rpg.prueba.levels;

import java.util.HashMap;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.rpg.prueba.sprites.Sprite;
import com.rpg.prueba.sprites.StationarySprite;



public class Level {

    protected TiledMap map;
    protected Array<Sprite> sprites;
    protected Array<StationarySprite> stationarySprites;
    
    protected float red;
    protected float green;
    protected float blue;

    public Level() {
        sprites = new Array<Sprite>();
        stationarySprites = new Array<StationarySprite>();
      
    }

    public Array<StationarySprite> getStationarySprites() {
        return stationarySprites;
    }

    public TiledMap getMap() {
        return map;
    }

    public Array<Sprite> getSprites() {
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
