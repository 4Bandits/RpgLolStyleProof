package com.rpg.prueba.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector3;
import com.rpg.prueba.sprites.ChampionSprite;

public class PlayerInputProcessor {

	Champion player;
	public PlayerInputProcessor(Champion champion) {
		player =champion;
	}
	public void handleInputOn(View window){
		   if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
	           Gdx.app.exit();
	       }
	       if (isHeadingOnlyUp() ) {
	    	   player.setState(ChampionSprite.State.walkBack);
	    	   player.setDY(ChampionSprite.MAX_VELOCITY);
	    	   player.setDX(0f);
	       }
	       if (isHeadingOnlyDown()) {
	           player.setState(ChampionSprite.State.walkFront);
	           player.setDY(-ChampionSprite.MAX_VELOCITY);
	           player.setDX(0f);
	       }
	       if (isHeadingOnlyLeft()) {
	           player.setFacingLeft(true);
	           player.setState(ChampionSprite.State.walkLeft);
	           player.setDX(-ChampionSprite.MAX_VELOCITY);
	           player.setDY(0f);
	       }
	       if (isHeadingOnlyRight()) {
	           player.setFacingLeft(false);
	           player.setState(ChampionSprite.State.walkRight);
	           player.setDX(ChampionSprite.MAX_VELOCITY);
	           player.setDY(0f);
	       }
	       
	       if(isHeadingUpAndRight()){
	       	 player.setFacingLeft(false);
	            player.setState(ChampionSprite.State.walkRight);
	            player.setDX(ChampionSprite.MAX_VELOCITY/1.5f);
	            player.setDY(ChampionSprite.MAX_VELOCITY/1.5f);
	       }
	       if(isHeadingUpAndLeft()){
	      	 player.setFacingLeft(true);
	           player.setState(ChampionSprite.State.walkLeft);
	           player.setDX(-ChampionSprite.MAX_VELOCITY/1.5f);
	           player.setDY(ChampionSprite.MAX_VELOCITY/1.5f);
	      }
	       if(isHeadingDownAndRight()){
	      	 player.setFacingLeft(false);
	           player.setState(ChampionSprite.State.walkRight);
	           player.setDX(ChampionSprite.MAX_VELOCITY/1.5f);
	           player.setDY(-ChampionSprite.MAX_VELOCITY/1.5f);
	      }
	       if(isHeadingDownAndLeft()){
	      	 	player.setFacingLeft(true);
	           player.setState(ChampionSprite.State.walkLeft);
	           player.setDX(-ChampionSprite.MAX_VELOCITY/1.5f);
	           player.setDY(-ChampionSprite.MAX_VELOCITY/1.5f);
	      }
	       if(isAtackingLeft()){
	       	player.setFacingLeft(true);
	           player.setState(ChampionSprite.State.attackLeft);
	           player.setDX(0f);
	           player.setDY(0f);
	       }
	       if(isAtackingRight()){
	       	player.setFacingLeft(false);
	           player.setState(ChampionSprite.State.attackRight);
	           player.setDX(0f);
	           player.setDY(0f);
	       }
	       if(isAtackingFront()){
	       	player.setFacingLeft(false);
	           player.setState(ChampionSprite.State.attackFront);
	           player.setDX(0f);
	           player.setDY(0f);
	       }
	       if(isAtackingBack()){
	       	player.setFacingLeft(false);
	           player.setState(ChampionSprite.State.attackBack);
	           player.setDX(0f);
	           player.setDY(0f);
	       }

	       if (Gdx.input.justTouched()) {
	           Vector3 point = window.getWorldPoint(Gdx.input.getX(), Gdx.input.getY());
	           player.setPosition(point.x, point.y);

	       }
	   }
	   private boolean isAtackingLeft(){
	   	return ((player.getState()== ChampionSprite.State.attackLeft) || (player.getState()== ChampionSprite.State.standLeft) || (player.getState()== ChampionSprite.State.walkLeft)) && Gdx.input.isKeyPressed(Input.Keys.SPACE);
	   }
	   private boolean isAtackingRight(){
	   	return ((player.getState()== ChampionSprite.State.attackRight) || (player.getState()== ChampionSprite.State.standRight) || (player.getState()== ChampionSprite.State.walkRight)) && Gdx.input.isKeyPressed(Input.Keys.SPACE);
	   }
	   private boolean isAtackingFront(){
	   	return ((player.getState()== ChampionSprite.State.attackFront) || (player.getState()== ChampionSprite.State.standFront) || (player.getState()== ChampionSprite.State.walkFront)) && Gdx.input.isKeyPressed(Input.Keys.SPACE);
	   }
	   private boolean isAtackingBack(){
	   	return ((player.getState()== ChampionSprite.State.attackBack) || (player.getState()== ChampionSprite.State.standBack) || (player.getState()== ChampionSprite.State.walkBack)) && Gdx.input.isKeyPressed(Input.Keys.SPACE);
	   }
	   private boolean isHeadingUpAndRight() {
			return (Gdx.input.isKeyPressed(Input.Keys.UP) || 
				   Gdx.input.isKeyPressed(Input.Keys.W)) && 
				     (!(Gdx.input.isKeyPressed(Input.Keys.LEFT) || 
				       Gdx.input.isKeyPressed(Input.Keys.A))) && 
				        ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || 
				          Gdx.input.isKeyPressed(Input.Keys.D)));
		}
	   private boolean isHeadingUpAndLeft() {
			return (Gdx.input.isKeyPressed(Input.Keys.UP) || 
				   Gdx.input.isKeyPressed(Input.Keys.W)) && 
				     ((Gdx.input.isKeyPressed(Input.Keys.LEFT) || 
				       Gdx.input.isKeyPressed(Input.Keys.A)) && 
				        !((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || 
				          Gdx.input.isKeyPressed(Input.Keys.D))));
		}
	   private boolean isHeadingDownAndRight() {
			return (Gdx.input.isKeyPressed(Input.Keys.DOWN) || 
				   Gdx.input.isKeyPressed(Input.Keys.S)) && 
				     !(Gdx.input.isKeyPressed(Input.Keys.LEFT) || 
				       Gdx.input.isKeyPressed(Input.Keys.A)) && 
				        ((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || 
				          Gdx.input.isKeyPressed(Input.Keys.D)));
		}
	   private boolean isHeadingDownAndLeft() {
			return (Gdx.input.isKeyPressed(Input.Keys.DOWN) || 
				   Gdx.input.isKeyPressed(Input.Keys.S)) && 
				     (Gdx.input.isKeyPressed(Input.Keys.LEFT) || 
				       Gdx.input.isKeyPressed(Input.Keys.A)) && 
				        !((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || 
				          Gdx.input.isKeyPressed(Input.Keys.D)));
		}
		private boolean isHeadingOnlyUp() {
			return (Gdx.input.isKeyPressed(Input.Keys.UP) || 
				   Gdx.input.isKeyPressed(Input.Keys.W)) && 
				     !(Gdx.input.isKeyPressed(Input.Keys.LEFT) || 
				       Gdx.input.isKeyPressed(Input.Keys.A)) && 
				        !((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || 
				          Gdx.input.isKeyPressed(Input.Keys.D)));
		}
		private boolean isHeadingOnlyDown() {
			return (Gdx.input.isKeyPressed(Input.Keys.DOWN) || 
				   Gdx.input.isKeyPressed(Input.Keys.S)) && 
				     !(Gdx.input.isKeyPressed(Input.Keys.LEFT) || 
				       Gdx.input.isKeyPressed(Input.Keys.A)) && 
				        !((Gdx.input.isKeyPressed(Input.Keys.RIGHT) || 
				          Gdx.input.isKeyPressed(Input.Keys.D)));
		}
		private boolean isHeadingOnlyLeft() {
			return (Gdx.input.isKeyPressed(Input.Keys.LEFT) || 
				   Gdx.input.isKeyPressed(Input.Keys.A)) && 
				     !(Gdx.input.isKeyPressed(Input.Keys.UP) || 
				       Gdx.input.isKeyPressed(Input.Keys.W)) && 
				        !((Gdx.input.isKeyPressed(Input.Keys.DOWN) || 
				          Gdx.input.isKeyPressed(Input.Keys.S)));
		}
		private boolean isHeadingOnlyRight() {
			return (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || 
				   Gdx.input.isKeyPressed(Input.Keys.D)) && 
				     !(Gdx.input.isKeyPressed(Input.Keys.UP) || 
				       Gdx.input.isKeyPressed(Input.Keys.W)) && 
				        !((Gdx.input.isKeyPressed(Input.Keys.DOWN) || 
				          Gdx.input.isKeyPressed(Input.Keys.S)));
		}
	    
}
