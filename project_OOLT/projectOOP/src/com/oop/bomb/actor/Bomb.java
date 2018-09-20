package com.oop.bomb.actor;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Bomb{
	private int x, y, width, height;
	protected Image img;
	protected int size, timeline;
	private Actor owner;
	
	public Bomb(int x, int y, int size, int timeline, Actor owner){
		x=(x/45)*45;
		y=(y/45)*45;
		this.x=x;
		this.y=y;
		this.size=size;
		this.timeline = timeline;	
		this.owner = owner;
		
		img= new ImageIcon(getClass().getResource("/Images/bomb.png")).getImage();
		this.width= img.getWidth(null);
		this.height= img.getHeight(null);
	}
	
	
	
	public void drawActor(Graphics2D g2d){
		g2d.drawImage(img, x, y, null);
	}
	

	
	public boolean setRun(Bomber bomber){
		Rectangle rec2 = new Rectangle(x, y, 45, 45);
		Rectangle rec3 = new Rectangle(bomber.getX(), bomber.getY(), bomber.getWidth(), bomber.getHeight());
		return rec2.intersects(rec3);
	}

	public boolean isImpact(int xNewBomb, int yNewBomb){
		Rectangle rec1 = new Rectangle(x, y, 45, 45);
		Rectangle rec2 = new Rectangle(xNewBomb, yNewBomb, 45, 45);
		return rec1.intersects(rec2);
	}
	
	
	public int isImpactBombvsActor(Actor actor){
		Rectangle rec2 = new Rectangle(x, y, 45, 45);
		Rectangle rec3 = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
		if(rec2.intersects(rec3)){
			if(actor.getType()==Bomber.BOSS){
				return 2;
			}
			return 1;
		};
		return 0;
	}
	
	public void setTimeline(int timeline) {
		this.timeline = timeline;
	}
	
	public void deadlineBomb(){
		this.timeline--;
	}

	public int getTimeline() {
		return timeline;
	}

	public int getSize() {
		return size;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}



	public Actor getOwner() {
		return owner;
	}



	public void setOwner(Actor owner) {
		this.owner = owner;
	}
	
}
