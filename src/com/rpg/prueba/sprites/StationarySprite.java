package com.rpg.prueba.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StationarySprite extends Sprite {

    private Animation animation;
    private float size;
    private TextureRegion headTexture;

    public StationarySprite(String textureFile, float x, float y, int columns, int size, float rate) {
        super(x, y);
        texture=new Texture(textureFile);
        this.size = size / 48f;
        animation = new Animation(rate, getFrames(texture, 0, columns, size));
        headTexture = new TextureRegion(texture, 0, -48, size, size);
    }

    public TextureRegion getAnimationFrame() {
        return animation.getKeyFrame(getAnimationTime(), true);
    }

    public float getSize() {
        return size;
    }

    public TextureRegion getHeadTexture() {
        return headTexture;
    }
}

