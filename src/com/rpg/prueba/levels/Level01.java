package com.rpg.prueba.levels;

import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;
import com.rpg.prueba.entities.Vampire;
import com.rpg.prueba.entities.Werewolf;
import com.rpg.prueba.entities.Wizard;
import com.rpg.prueba.sprites.StationarySprite;


public class Level01 extends Level {

    public Level01() {
        map = new TmxMapLoader().load("maps/level01.tmx");

        //Werewolf player = new Werewolf(50, 50, 80);
        //Wizard player = new Wizard(50, 50, 78);
        Vampire player = new Vampire(50, 50, 48);

        sprites.add(player);

        StationarySprite fire = new StationarySprite("images/fire.png", 55, 67, 4, 48, 0.3f);
        StationarySprite girl = new StationarySprite("images/villagegirl.png", 52, 50, 2, 72, 0.9f);
        StationarySprite forestGuy = new StationarySprite("images/forestnpc.png", 53, 68, 2, 72, 0.9f);

        stationarySprites.add(fire);
        stationarySprites.add(girl);
        stationarySprites.add(forestGuy);

        exits.put(new Vector3(73.2f, 35.8f, 0), "cave");

        red = 74f/255f;
        green = 160f/255f;
        blue = 223f/255f;
    }
}
