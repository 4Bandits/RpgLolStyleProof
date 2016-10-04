package com.rpg.prueba.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public class AtackSprite extends Sprite{

	private Vector2 previousPosition;
    private float speed;
    private boolean used;

    public AtackSprite(Texture texture, float x, float y) {
        super(texture);
        this.setPosition(x,y);
        this.speed = 10;
        this.used = false;
    }

    public void update(float delta) {
        this.translate(1 * speed * 1, 1 * speed * 1);
    }

    public void render(SpriteBatch batch) {
        if(used)
            this.draw(batch);
    }

    public void updateCoordinates(float x, float y) {
        this.setPosition(x, y);
    }

    public void setUsed() {
        this.used = !this.used;
    }

    public boolean isUsed() {
        return used;
    }
}
