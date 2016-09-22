package com.rpg.prueba.entities;

import com.badlogic.gdx.graphics.g2d.Animation;


public class Werewolf extends Champion {

    public Werewolf(float x, float y, int size) {
        super( x, y);
        texture = GameContentManager.werewolf;
        // Animations
        standFrontAnimation = new Animation(0.3f, getFrames(texture, 0, 1, size));
        standBackAnimation = new Animation(0.3f, getFrames(texture, 3, 1, size));
        standLeftAnimation = new Animation(0.3f, getFrames(texture, 2, 1, size));
        standRightAnimation = new Animation(0.3f, getFrames(texture, 2, 1, size));
        walkFrontAnimation = new Animation(0.3f, getFrames(texture, 0, 2, size));
        walkBackAnimation = new Animation(0.3f, getFrames(texture, 3, 2, size));
        walkLeftAnimation = new Animation(0.3f, getFrames(texture, 2, 2, size));
        walkRightAnimation = new Animation(0.3f, getFrames(texture, 2, 2, size));
        /*attackFrontAnimation = new Animation(0.3f, getFrames(texture, 3, 4, size));
        attackBackAnimation = new Animation(0.3f, getFrames(texture, 2, 4, size));
        attackLeftAnimation = new Animation(0.3f, getFrames(texture, 1, 4, size));
        attackRightAnimation = new Animation(0.3f, getFrames(texture, 1, 4, size));
        deathAnimation = new Animation(0.3f, getFrames(texture, 0, 4, size));
*/
        // Attirbutes
        name = "Werewolf";
        
        HP=750;
        mana=250;
         
       
        movSpeed=25;
        atackSpeed=30;

        
        AP=0;
        AD=25;
        
    
        armor=100;
        magicResistance=50;
      
    }
}
