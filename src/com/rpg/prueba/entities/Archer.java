package com.rpg.prueba.entities;

import com.badlogic.gdx.graphics.g2d.Animation;


public class Archer extends Champion {

    public Archer(float x, float y, int size) {
        super(x, y);
        //texture = GameContentManager.archer;
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
        name = "Archer";
        
        HP=600;
        mana=350;
         
       
        movSpeed=30;
        atackSpeed=25;

        
        AP=0;
        AD=25;
        
    
        armor=80;
        magicResistance=20;
      
    }
}


