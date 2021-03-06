package com.rpg.prueba.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.rpg.prueba.entities.View;
import com.rpg.prueba.levels.Level;
import com.rpg.prueba.levels.Level01;
import com.rpg.prueba.sprites.ChampionSprite;
import com.rpg.prueba.sprites.StationarySprite;

public class Game implements ApplicationListener {

   
    private Array<Level> levels;

    private TiledMap map;

    private Array<ChampionSprite> sprites;

    private Level currentLevel;
    private Level01 level1;

    private View view;

    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject () {
            return new Rectangle();
        }
    };

    private Pool<View> viewPool = new Pool<View>() {
        @Override
        protected View newObject() {
            return new View(currentLevel);
        }
    };

    private Array<Rectangle> tiles = new Array<Rectangle>();

	@Override
	public void create() {
        

        levels = new Array<Level>();
        
         level1 = new Level01();
    
        
        levels.add(level1);
       
        currentLevel = level1;
        
        map = currentLevel.getMap();
        sprites = currentLevel.getSprites();

        view = viewPool.obtain();
	}

    @Override
    public void resize(int width, int height) {
        view.resize(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void render() {

        view.getInput();
        updateSprites(Gdx.graphics.getDeltaTime());
        view.render();
    }

    @Override
    public void dispose() {
        for (StationarySprite sprite : level1.getStationarySprites()) {
            sprite.getTexture().dispose();
        }
        for (Level level : levels) {
            for (ChampionSprite sprite : level.getSprites()) {
                sprite.getTexture().dispose();
            }
            level.getMap().dispose();
        }
        view.getStage().dispose();
        for (BitmapFont font : view.getFonts()) {
            font.dispose();
        }
    }

    private void updateSprites(float deltaTime) {
        for (ChampionSprite sprite : sprites) {
            // Check for map edges.
            if (sprite.getX() < 0) {
                sprite.setX(0);
            }
            if (sprite.getX() > 100 - ChampionSprite.SIZE) {
                sprite.setX(100 - ChampionSprite.SIZE);
            }
            if (sprite.getY() < 1) {
                sprite.setY(1);
            }
            if (sprite.getY() > 100 - ChampionSprite.SIZE) {
                sprite.setY(100 - ChampionSprite.SIZE);
            }

            // Manage velocity and switch states.
            if (Math.abs(sprite.getDX()) > ChampionSprite.MAX_VELOCITY) {
                sprite.setDX(Math.signum(sprite.getDX()) * ChampionSprite.MAX_VELOCITY);
            }
            if (Math.abs(sprite.getDY()) > ChampionSprite.MAX_VELOCITY) {
                sprite.setDY(Math.signum(sprite.getDY()) * ChampionSprite.MAX_VELOCITY);
            }
            if (Math.abs(sprite.getDX()) < 1) {
                sprite.setDX(0);

                switch (sprite.getState()) {
                    case walkLeft:
                        sprite.setState(ChampionSprite.State.standLeft);
                        break;
                    case walkRight:
                        sprite.setState(ChampionSprite.State.standRight);
                        break;
				default:
					break;
                }
            }
            if (Math.abs(sprite.getDY()) < 1) {
                sprite.setDY(0);

                switch (sprite.getState()) {
                    case walkFront:
                        sprite.setState(ChampionSprite.State.standFront);
                        break;
                    case walkBack:
                        sprite.setState(ChampionSprite.State.standBack);
                        break;
				default:
					break;
                }
            }
            sprite.getVelocity().scl(deltaTime);

            detectCollisions(sprite, 5);
            detectCollisions(sprite, 4);

            // Scale the velocity by the inverse delta time and set the latest position.
            sprite.getPosition().add(sprite.getVelocity());
            sprite.getVelocity().scl(1 / deltaTime);

            // Apply damping to the velocity so the sprite doesn't walk infinitely once a key is pressed.
            sprite.setDX(sprite.getDX() * ChampionSprite.DAMPING);
            sprite.setDY(sprite.getDY() * ChampionSprite.DAMPING);
            sprite.update(deltaTime);

        }
    }

    private void setupLevel() {
        map = currentLevel.getMap();
        sprites = currentLevel.getSprites();
        viewPool.free(view);
        viewPool.clear();
        view = viewPool.obtain();
    }

    public void setTiles(int startX, int startY, int endX, int endY, Array<Rectangle> tiles, int layerIndex) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerIndex);
        rectPool.freeAll(tiles);
        tiles.clear();
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    Rectangle rect = rectPool.obtain();
                    rect.set(x + 0.2f, y - 0.2f, 0.8f, 0.8f);
                    tiles.add(rect);
                }
            }
        }
    }

    public void detectCollisions(ChampionSprite sprite, int layerIndex) {
        Rectangle spriteRect = rectPool.obtain();
        spriteRect.set(sprite.getX() + 0.3f, sprite.getY() - 0.3f, ChampionSprite.SIZE - 0.6f, ChampionSprite.SIZE - 0.6f);
        int startX=0, startY=0, endX=0, endY=0;

        // X-Axis
        this.xAxisCollisions(startX,startY,endX,endY,sprite, layerIndex, spriteRect);

        // Tile collision on the x-axis.
        this.tileCollisionOnXAxis(sprite, layerIndex, spriteRect);

        // Sprite collision on the x-axis.
        this.spriteCollisionOnXAxis(sprite, spriteRect);

        

        // Y-Axis
        this.yAxisCollision(startX,startY,endX,endY,sprite, layerIndex, spriteRect);

        // Tile collision on the y-axis.
        this.tileCollisionOnYAxis(sprite, layerIndex, spriteRect);

        // Sprite collision on the y-axis.
        this.spriteCollisionOnYAxis(sprite, spriteRect);
    }

	private void spriteCollisionOnYAxis(ChampionSprite sprite, Rectangle spriteRect) {
		for (ChampionSprite element : sprites) {
            Rectangle elemRect = rectPool.obtain();
            elemRect.set(element.getX() + 0.3f, element.getY() - 0.3f,
                    ChampionSprite.SIZE - 0.6f, ChampionSprite.SIZE - 0.6f);
            if (element != sprite) {
                if (spriteRect.overlaps(elemRect)) {
                    sprite.setDY(0);
                    break;
                }
            }
        }

        spriteRect.y = sprite.getY() - 0.3f;

        rectPool.free(spriteRect);
	}

	private void tileCollisionOnYAxis(ChampionSprite sprite, int layerIndex, Rectangle spriteRect) {
		for (Rectangle tile: tiles) {
            if (spriteRect.overlaps(tile)) {
                sprite.setDY(0);
              
            }
        }
	}

	private void yAxisCollision(int startX,int startY,int endX,int endY,ChampionSprite sprite, int layerIndex, Rectangle spriteRect) {
		
		if (sprite.getDY() > 0) {
            startY = endY = (int)(sprite.getY() + ChampionSprite.SIZE + sprite.getDY());
        } else {
            startY = endY = (int)(sprite.getY() + sprite.getDY());
        }
        startX = (int)(sprite.getX());
        endX = (int)(sprite.getX() + ChampionSprite.SIZE);
        setTiles(startX, startY, endX, endY, tiles, layerIndex);
        spriteRect.y += sprite.getDY();
	}

	private void spriteCollisionOnXAxis(ChampionSprite sprite, Rectangle spriteRect) {
		for (ChampionSprite element : sprites) {
            Rectangle elemRect = rectPool.obtain();
            elemRect.set(element.getX() + 0.3f, element.getY() - 0.3f,
                         ChampionSprite.SIZE - 0.6f, ChampionSprite.SIZE - 0.6f);
            if (element != sprite) {
                if (spriteRect.overlaps(elemRect)) {
                    sprite.setDX(0);
                    break;
                }
            }
        }
		spriteRect.x = sprite.getX() + 0.3f;
	}

	private void tileCollisionOnXAxis(ChampionSprite sprite, int layerIndex, Rectangle spriteRect) {
		for (Rectangle tile: tiles) {
            if(spriteRect.overlaps(tile)) {
                sprite.setDX(0);
               
            }
        }
	}

	private void xAxisCollisions(int startX,int startY,int endX,int endY,ChampionSprite sprite, int layerIndex, Rectangle spriteRect) {
		
		if(sprite.getDX() > 0) {
            startX = endX = (int)(sprite.getX() + ChampionSprite.SIZE + sprite.getDX());
        } else {
            startX = endX = (int)(sprite.getX() + sprite.getDX());
        }
        startY = (int)(sprite.getY());
        endY = (int)(sprite.getY() + ChampionSprite.SIZE);
        setTiles(startX, startY, endX, endY, tiles, layerIndex);
        spriteRect.x += sprite.getDX();
	}

 
}
