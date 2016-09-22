package com.rpg.prueba.entities;

import com.rpg.prueba.sprites.Sprite;

public class Champion extends Sprite {

   protected String name;
    
   protected int HP;
   protected int mana;
    
    //Speed
   protected int movSpeed;
   protected int atackSpeed;

    // Atack
   protected int AP;
   protected int AD;
   
    // Defense
   protected int armor;
   protected int magicResistance;

   public Champion (float x, float y){
	   super(x,y);
   }
    

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getHP() {
		return HP;
	}



	public void setHP(int hP) {
		HP = hP;
	}



	public int getMana() {
		return mana;
	}



	public void setMana(int mana) {
		this.mana = mana;
	}



	public int getMovSpeed() {
		return movSpeed;
	}



	public void setMovSpeed(int movSpeed) {
		this.movSpeed = movSpeed;
	}



	public int getAtackSpeed() {
		return atackSpeed;
	}



	public void setAtackSpeed(int atackSpeed) {
		this.atackSpeed = atackSpeed;
	}



	public int getAP() {
		return AP;
	}



	public void setAP(int aP) {
		AP = aP;
	}



	public int getAD() {
		return AD;
	}



	public void setAD(int aD) {
		AD = aD;
	}



	public int getArmor() {
		return armor;
	}



	public void setArmor(int armor) {
		this.armor = armor;
	}



	public int getMagicResistance() {
		return magicResistance;
	}



	public void setMagicResistance(int magicResistance) {
		this.magicResistance = magicResistance;
	}

   
   }
