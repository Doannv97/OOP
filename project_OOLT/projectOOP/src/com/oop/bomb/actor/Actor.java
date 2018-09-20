package com.oop.bomb.actor;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;


public abstract class Actor {
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int UP = 3;
	public static final int DOWN = 4;
	public static final int ALIVE= 1;
	public static final int DEAD = 0;
	public static final int BOMBER = 1;
	public static final int MONSTER = 2;
	public static final int BOSS = 3;
	public static final int BOMB = 4;
	
	
	protected int x,y,type,orient, speed, width, height;
	protected int sizeBomb,quantityBomb,status, heart;
	protected Image img;
	protected int newX, newY;
	protected int slBombDaSD;
	protected int dem = 0;
	
	
	public Actor(int x, int y, int type, int orient, int speed, int sizebomb, int quantityBomb) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.orient = orient;
		this.speed = speed;
		this.sizeBomb = sizebomb;
		this.quantityBomb = quantityBomb;
		
		this.slBombDaSD = 0;
		this.status = Actor.ALIVE;
	
		this.newX = x;
		this.newY = y;
	}
	
	public abstract void drawActor(Graphics2D g2d);
	
	public abstract void changeOrient(int orient);
	
	public boolean checkToaDo() {
		if (x == newX && y == newY) return true; else return false;
	}
	
	public void animation() {
		if (x == newX && y == newY) return;
		switch (orient) {
		case LEFT:
			x -= 1;
			break;
		case RIGHT:
			x += 1;
			break;
		case UP:
			y -= 1;
			break;
		case DOWN:
			y += 1;
			break;
		}
	}
	
	public boolean isBombOrBox(int x, int y, ArrayList<Bomb> arrBomb, ArrayList<Box> arrBox){
		if (x < 0 || y < 0 || x > 630 || y > 540) return true;
		Rectangle rec1 = new Rectangle(x, y, 45, 45);
		for(int i=0;i<arrBox.size();i++){
			Box box = arrBox.get(i);
			Rectangle rec2 = new Rectangle(box.getX(), box.getY(), box.getWidth(), box.getHeight());
			if (rec1.intersects(rec2))
				return true;
		}
		for (int i=0; i<arrBomb.size(); i++) {
			Bomb bomb = arrBomb.get(i);
			Rectangle rec2 = new Rectangle(bomb.getX(), bomb.getY(), bomb.getWidth(), bomb.getHeight());
			if (rec1.intersects(rec2))
				return true;
		}
		return false;
	}
	
	
	public boolean move(int orient, ArrayList<Bomb> arrBomb, ArrayList<Box> arrBox){
		if(status==DEAD){
			return false;
		}
		
		switch (orient) {
		case LEFT:
			if(x<45)	return false;						
			if (isBombOrBox(x-45, y, arrBomb, arrBox)) return false;
			else newX= x-45;	
			
			break;		
			
		case RIGHT:
			if(x>585)	return false;						
			if (isBombOrBox(x+45, y, arrBomb, arrBox)) return false;
			else newX= x+45;	
			
			break;
			
		case UP:
			if(y<45)	return false;			
			if (isBombOrBox(x, y-45, arrBomb, arrBox)) return false;
			else newY=y-45;
			break;
			
		case DOWN:
			if(y>495)	return false;
			if (isBombOrBox(x, y+45, arrBomb, arrBox)) return false;
			else	newY=y+45;
			break;

		default:
			break;
		}
		return true;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getOrient() {
		return orient;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		if (speed>50) this.speed = speed;
	}
	
	public void setImg(Image img) {
		this.img = img;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getQuantityBomb() {
		return quantityBomb;
	}

	public void setQuantityBomb(int quantityBomb) {
		this.quantityBomb = quantityBomb;
	}


	public void setSizeBomb(int sizeBomb) {
		if (sizeBomb<8) this.sizeBomb = sizeBomb;
	}

	public int getSizeBomb() {
		return sizeBomb;
	}

	public int getType() {
		return type;
	}
	
	public int getHeart() {
		return heart;
	}

	public void setHeart(int heart) {
		this.heart = heart;
	}

	public int getNewX() {
		return newX;
	}

	public void setNewX(int newX) {
		this.newX = newX;
	}

	public int getNewY() {
		return newY;
	}

	public void setNewY(int newY) {
		this.newY = newY;
	}

	public int getSlBombDaSD() {
		return slBombDaSD;
	}

	public void setSlBombDaSD(int slBombDaSD) {
		this.slBombDaSD = slBombDaSD;
	}
	
	public int getDem() {
		return dem;
	}
	public void setDem(int dem) {
		this.dem = dem;
	}
}
