package com.rpg.prueba.entities;

import com.badlogic.gdx.graphics.g2d.Animation;


public class Wizard extends Champion {

    public Wizard(float x, float y, int size) {
        super(x, y);
        texture = GameContentManager.wizard;
        // Animations
        standFrontAnimation = new Animation(0.3f, getFrames(texture, 9, 6, size));
        standBackAnimation = new Animation(0.3f, getFrames(texture, 8, 6, size));
        standLeftAnimation = new Animation(0.3f, getFrames(texture, 7, 6, size));
        standRightAnimation = new Animation(0.3f, getFrames(texture, 7, 6, size));
        walkFrontAnimation = new Animation(0.3f, getFrames(texture, 6, 4, size));
        walkBackAnimation = new Animation(0.3f, getFrames(texture, 5, 4, size));
        walkLeftAnimation = new Animation(0.3f, getFrames(texture, 4, 4, size));
        walkRightAnimation = new Animation(0.3f, getFrames(texture, 4, 4, size));
        deathAnimation = new Animation(0.3f, getFrames(texture, 0, 4, size));

        // Attirbutes
        name = "Wizard";
        
        HP=450;
        mana=700;
         
       
        movSpeed=35;
        atackSpeed=20;

        
        AP=40;
        AD=0;
        
    
        armor=50;
        magicResistance=20;
      
    }
}