package com.oop.bomb.actor;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.BitSet;
import javax.swing.ImageIcon;



public class Bomber extends Actor{	
	private int score;
	
	public Bomber(int x, int y, int type, int orient, int speed, int sizebomb, int quantityBomb) {
		
		super(x, y, type, orient, speed, sizebomb, quantityBomb);		
		this.heart = 3;
		this.score=0;		
		this.img = new ImageIcon(getClass().getResource("/Images/bomber_down.png")).getImage();
		width = img.getWidth(null);
		height = img.getHeight(null)-20;
	
	}

	public void setNew(int x,int y) {
		this.x = x;
		this.y = y;
		this.newX = x;
		this.newY = y;
		this.status = ALIVE;
		this.img = new ImageIcon(getClass().getResource("/Images/bomber_down.png")).getImage();
	}
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	@Override
	public void drawActor(Graphics2D g2d){
		g2d.drawImage(img, x, y-20, null);
	}
	
	@Override
	public void changeOrient(int orient) {
		if(this.status==DEAD){
			return;
		}
		this.orient = orient;
		switch (orient) {
		case LEFT:
			img = new ImageIcon(getClass().getResource("/Images/bomber_left.png")).getImage();
			break;
		case RIGHT:
			img = new ImageIcon(getClass().getResource("/Images/bomber_right.png")).getImage();
			break;
		case UP:
			img = new ImageIcon(getClass().getResource("/Images/bomber_up.png")).getImage();
			break;
		case DOWN:
			img = new ImageIcon(getClass().getResource("/Images/bomber_down.png")).getImage();
			break;
		default:
			break;
		}
	}

	
	
	public void diChuyen(BitSet traceKey, ArrayList<Bomb> arrBomb, ArrayList<Box> arrBox) {		
		this.dem++;
		
		if (this.dem % (this.getSpeed() / 50) == 0) this.animation();		
		
		
		if (this.checkToaDo()) {
		
			if(traceKey.get(KeyEvent.VK_LEFT)){
				this.dem =0;	
				this.changeOrient(Bomber.LEFT);
				this.move(Bomber.LEFT,arrBomb,arrBox);
			}
			else if(traceKey.get(KeyEvent.VK_RIGHT)){
				this.dem =0;	
				this.changeOrient(Bomber.RIGHT);
				this.move(Bomber.RIGHT,arrBomb,arrBox);
			}
			else if(traceKey.get(KeyEvent.VK_UP)){
				this.dem =0;	
				this.changeOrient(Bomber.UP);
				this.move(Bomber.UP,arrBomb,arrBox);;
			}
			else if(traceKey.get(KeyEvent.VK_DOWN)){
				this.dem =0;	
				this.changeOrient(Bomber.DOWN);
				this.move(Bomber.DOWN,arrBomb,arrBox);
			}
			else	this.dem -= 20;
				
		}
	
	}
	
	public void datBomb(BitSet traceKey, Manager manager) {
		
		if(traceKey.get(KeyEvent.VK_SPACE)){				
			manager.innitBomb(this);
		}
	}
	
	public boolean isImpactBomberVsActor(Actor actor){
		if(status==DEAD){
			return false;
		}
		Rectangle rec1 = new Rectangle(x, y, width, height);
		Rectangle rec2 = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
		return rec1.intersects(rec2);
	}
	
	
}
