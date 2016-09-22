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
import com.rpg.prueba.sprites.Sprite;

public class Game implements ApplicationListener {

   
    private Array<Level> levels;

    //private HashMap<String, State> states;

    private TiledMap map;

    private Array<Sprite> sprites;

    /*private enum State {
        level01State,
        houseState,
        caveState,
        cave02State
    }

    private State state;*/

    //private boolean stateChanged;

    private Level currentLevel;
    private Level01 level1;
    /*private Cave cave;
    private Cave02 cave2;*/

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
        /* cave = new Cave();
         cave2= new Cave02();
        states = new HashMap<String, State>(3);
        states.put("level01", State.level01State);
        states.put("cave", State.caveState);
        states.put("cave02", State.cave02State);*/
        
        levels.add(level1);
        /*levels.add(cave);
        levels.add(cave2);
       

        state = State.level01State;*/
        //stateChanged = false;
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
        /*if (stateChanged) {
            switch (state) {
                case level01State:
                    currentLevel = level1;
                    setupLevel();
                    break;
                case caveState:
                    currentLevel = cave;
                    setupLevel();
                    break;
                case cave02State:
                    currentLevel = cave2;
                    setupLevel();
                    break;
            }
            stateChanged = false;
        }*/

        view.getInput();
        updateSprites(Gdx.graphics.getDeltaTime());
        view.render();
    }

    @Override
    public void dispose() {
       /* for (StationarySprite sprite : level01.getStationarySprites()) {
            sprite.getTexture().dispose();
        }*/
        for (Level level : levels) {
            for (Sprite sprite : level.getSprites()) {
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
        for (Sprite sprite : sprites) {
            // Check for map edges.
            if (sprite.getX() < 0) {
                sprite.setX(0);
            }
            if (sprite.getX() > 100 - Sprite.SIZE) {
                sprite.setX(100 - Sprite.SIZE);
            }
            if (sprite.getY() < 1) {
                sprite.setY(1);
            }
            if (sprite.getY() > 100 - Sprite.SIZE) {
                sprite.setY(100 - Sprite.SIZE);
            }

            // Manage velocity and switch states.
            if (Math.abs(sprite.getDX()) > Sprite.MAX_VELOCITY) {
                sprite.setDX(Math.signum(sprite.getDX()) * Sprite.MAX_VELOCITY);
            }
            if (Math.abs(sprite.getDY()) > Sprite.MAX_VELOCITY) {
                sprite.setDY(Math.signum(sprite.getDY()) * Sprite.MAX_VELOCITY);
            }
            if (Math.abs(sprite.getDX()) < 1) {
                sprite.setDX(0);

                switch (sprite.getState()) {
                    case walkLeft:
                        sprite.setState(Sprite.State.standLeft);
                        break;
                    case walkRight:
                        sprite.setState(Sprite.State.standRight);
                        break;
				default:
					break;
                }
            }
            if (Math.abs(sprite.getDY()) < 1) {
                sprite.setDY(0);

                switch (sprite.getState()) {
                    case walkFront:
                        sprite.setState(Sprite.State.standFront);
                        break;
                    case walkBack:
                        sprite.setState(Sprite.State.standBack);
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
            sprite.setDX(sprite.getDX() * Sprite.DAMPING);
            sprite.setDY(sprite.getDY() * Sprite.DAMPING);

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

    public void detectCollisions(Sprite sprite, int layerIndex) {
        Rectangle spriteRect = rectPool.obtain();
        spriteRect.set(sprite.getX() + 0.3f, sprite.getY() - 0.3f, Sprite.SIZE - 0.6f, Sprite.SIZE - 0.6f);
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

	private void spriteCollisionOnYAxis(Sprite sprite, Rectangle spriteRect) {
		for (Sprite element : sprites) {
            Rectangle elemRect = rectPool.obtain();
            elemRect.set(element.getX() + 0.3f, element.getY() - 0.3f,
                    Sprite.SIZE - 0.6f, Sprite.SIZE - 0.6f);
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

	private void tileCollisionOnYAxis(Sprite sprite, int layerIndex, Rectangle spriteRect) {
		for (Rectangle tile: tiles) {
            if (spriteRect.overlaps(tile)) {
                sprite.setDY(0);
                /*if (layerIndex == 5) {
                    exitRoom(new Vector3(tile.getX(), tile.getY(), 0));
                }
                break;*/
            }
        }
	}

	private void yAxisCollision(int startX,int startY,int endX,int endY,Sprite sprite, int layerIndex, Rectangle spriteRect) {
		
		if (sprite.getDY() > 0) {
            startY = endY = (int)(sprite.getY() + Sprite.SIZE + sprite.getDY());
        } else {
            startY = endY = (int)(sprite.getY() + sprite.getDY());
        }
        startX = (int)(sprite.getX());
        endX = (int)(sprite.getX() + Sprite.SIZE);
        setTiles(startX, startY, endX, endY, tiles, layerIndex);
        spriteRect.y += sprite.getDY();
	}

	private void spriteCollisionOnXAxis(Sprite sprite, Rectangle spriteRect) {
		for (Sprite element : sprites) {
            Rectangle elemRect = rectPool.obtain();
            elemRect.set(element.getX() + 0.3f, element.getY() - 0.3f,
                         Sprite.SIZE - 0.6f, Sprite.SIZE - 0.6f);
            if (element != sprite) {
                if (spriteRect.overlaps(elemRect)) {
                    sprite.setDX(0);
                    break;
                }
            }
        }
		spriteRect.x = sprite.getX() + 0.3f;
	}

	private void tileCollisionOnXAxis(Sprite sprite, int layerIndex, Rectangle spriteRect) {
		for (Rectangle tile: tiles) {
            if(spriteRect.overlaps(tile)) {
                sprite.setDX(0);
                /*if (layerIndex == 5) {
                    exitRoom(new Vector3(tile.getX(), tile.getY(), 0));
                }
                break;*/
            }
        }
	}

	private void xAxisCollisions(int startX,int startY,int endX,int endY,Sprite sprite, int layerIndex, Rectangle spriteRect) {
		
		if(sprite.getDX() > 0) {
            startX = endX = (int)(sprite.getX() + Sprite.SIZE + sprite.getDX());
        } else {
            startX = endX = (int)(sprite.getX() + sprite.getDX());
        }
        startY = (int)(sprite.getY());
        endY = (int)(sprite.getY() + Sprite.SIZE);
        setTiles(startX, startY, endX, endY, tiles, layerIndex);
        spriteRect.x += sprite.getDX();
	}

   /* private void exitRoom(Vector3 tile) {
        for (Vector3 point : currentLevel.getExits().keySet()) {
            if (point.x == tile.x && point.y == tile.y) {
                state = states.get(currentLevel.getExits().get(point));
                stateChanged = true;
            }
        }
    }*/

    /*private void combat() {
        for (Sprite sprite : sprites) {

        }
    }*/
}
