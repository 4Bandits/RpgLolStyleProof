package com.rpg.prueba.entities;


import com.badlogic.gdx.graphics.g2d.Animation;


public class Warrior extends Champion {

    public Warrior(float x, float y, int size) {
        super(x, y);
        //texture = GameContentManager.warrior;
        // Animations
        standFrontAnimation = new Animation(0.3f, getFrames(texture, 9, 6, size));
        standBackAnimation = new Animation(0.3f, getFrames(texture, 8, 6, size));
        standLeftAnimation = new Animation(0.3f, getFrames(texture, 7, 6, size));
        standRightAnimation = new Animation(0.3f, getFrames(texture, 7, 6, size));
        walkFrontAnimation = new Animation(0.3f, getFrames(texture, 6, 4, size));
        walkBackAnimation = new Animation(0.3f, getFrames(texture, 5, 4, size));
        walkLeftAnimation = new Animation(0.3f, getFrames(texture, 4, 4, size));
        walkRightAnimation = new Animation(0.3f, getFrames(texture, 4, 4, size));
        attackFrontAnimation = new Animation(0.3f, getFrames(texture, 3, 4, size));
        attackBackAnimation = new Animation(0.3f, getFrames(texture, 2, 4, size));
        attackLeftAnimation = new Animation(0.3f, getFrames(texture, 1, 4, size));
        attackRightAnimation = new Animation(0.3f, getFrames(texture, 1, 4, size));
        deathAnimation = new Animation(0.3f, getFrames(texture, 0, 4, size));

        // Attirbutes
        name = "Warrior";
        
        HP=800;
        mana=250;
         
       
        movSpeed=20;
        atackSpeed=25;

        
        AP=0;
        AD=30;
        
    
        armor=100;
        magicResistance=50;
      
    }
}

