package com.rpg.prueba.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Vampire extends Champion {

	Texture texture_hurt;
	Texture texture_atk;
    public Vampire(float x, float y, int size) {
        super( x, y);
        texture = GameContentManager.vampire;
        texture_atk = GameContentManager.vampire_atk;
        texture_hurt = GameContentManager.vampire_hurt;
        // Animations
        standFrontAnimation = new Animation(0.3f, getFrames(texture, 0, 1, size));
        standBackAnimation = new Animation(0.3f, getFrames(texture, 3, 1, size));
        standLeftAnimation = new Animation(0.3f, getFrames(texture, 2, 1, size));
        standRightAnimation = new Animation(0.3f, getFrames(texture, 2, 1, size));
        
        walkFrontAnimation = new Animation(0.3f, getFrames(texture, 0, 2, size));
        walkBackAnimation = new Animation(0.3f, getFrames(texture, 3, 2, size));
        walkLeftAnimation = new Animation(0.3f, getFrames(texture, 2, 2, size));
        walkRightAnimation = new Animation(0.3f, getFrames(texture, 2, 2, size));
        
        /*attackFrontAnimation = new Animation(0.3f, getFrames(texture_atk, 0,1, size));
        attackBackAnimation = new Animation(0.3f, getFrames(texture_atk, 3, 1, size));
        attackLeftAnimation = new Animation(0.3f, getFrames(texture_atk, 2, 1, size));
        attackRightAnimation = new Animation(0.3f, getFrames(texture_atk, 2, 1, size));*/
        deathAnimation = new Animation(0.3f, getFrames(texture, 0, 4, size));

        // Attirbutes
        name = "Vampire";
        
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
