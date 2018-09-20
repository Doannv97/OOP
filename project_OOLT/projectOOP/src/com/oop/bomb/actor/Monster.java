package com.oop.bomb.actor;


import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class Monster extends Actor{
	private Random random = new Random(); 
	
	
	public Monster(int x, int y, int type, int orient, int speed, int sizebomb, int quantityBomb, String images) {
		
		super(x, y, type, orient, speed, sizebomb, quantityBomb);		
		
		this.heart = 1;
		this.img = new ImageIcon(getClass().getResource(images)).getImage();
		width = img.getWidth(null);
		if(type==Actor.MONSTER){
			height = img.getHeight(null)-23;
		}else{
			height = img.getHeight(null)-38;
		}
	}
	
	@Override
	public void drawActor(Graphics2D g2d){
		g2d.drawImage(img, x, y-23, null);
	}
		
		
	@Override
	public void changeOrient(int orient) {
		this.orient = orient;
		if(type==Actor.MONSTER){
			switch (orient) {
			case LEFT:
				img = new ImageIcon(getClass().getResource("/Images/monster_left.png")).getImage();
				break;
			case RIGHT:
				img = new ImageIcon(getClass().getResource("/Images/monster_right.png")).getImage();
				break;
			case UP:
				img = new ImageIcon(getClass().getResource("/Images/monster_up.png")).getImage();
				break;
			case DOWN:
				img = new ImageIcon(getClass().getResource("/Images/monster_down.png")).getImage();
				break;
			default:
				break;
			}
		}
	}

	
	private boolean checkViTriAnToan(int x, int y, ArrayList<Bomb> arrBomb, ArrayList<BombBang> arrBombBang) {
		if (x < 0 || y < 0 || x > 630 || y > 540) return false;
		
		for (int i=0; i<arrBombBang.size(); i++)
			if (arrBombBang.get(i).isBang(x, y, 45, 45)) return false;
		
		
		for (int i = 0; i < arrBomb.size(); i++) {
			Bomb bomb = arrBomb.get(i);
			
			if (bomb.getTimeline() < 1000) {
				if (x == bomb.getX() && Math.abs(y - bomb.getY()) <= 45*bomb.getSize()) {
					return false;
				}
				if (y == bomb.getY() && Math.abs(x - bomb.getX()) <= 45*bomb.getSize()) {
					return false;
				}
			}	
			
		}
		return true;
	}
	
	private boolean checkViTriDiChuyenDuoc(int x, int y, ArrayList<Bomb> arrBomb, ArrayList<Box> arrBox) {
		if (isBombOrBox(x,y,arrBomb, arrBox)) return false;
		if (!(isBombOrBox(x-45,y, arrBomb, arrBox))) return true;	
		if (!(isBombOrBox(x+45,y, arrBomb, arrBox))) return true;	
		if (!(isBombOrBox(x,y-45, arrBomb, arrBox))) return true;	
		if (!(isBombOrBox(x,y+45, arrBomb, arrBox))) return true;		
		return false;
	}
	
	public void diChuyen(ArrayList<Bomb> arrBomb, ArrayList<BombBang> arrBombBang, ArrayList<Box> arrBox) {
		
		if (this.getDem() % (this.getSpeed() / 50) == 0)
			this.animation();			
				
		
		if (this.getDem() > this.getSpeed() && this.checkToaDo()) {				
			int orient = 0, x2, y2;
			boolean nguyHiem = false;
			do {
				
				orient = random.nextInt(17)/4 + 1;
				
			
				if (orient == 5) {
					if (checkViTriAnToan(x, y, arrBomb, arrBombBang) || (nguyHiem)) break;
					else {
						nguyHiem = true;
						orient = random.nextInt(4) + 1;
					}
				}
				x2 = x; y2 = y;
				switch (orient) {
				case Actor.LEFT: 	x2=x-45;	break;
				case Actor.RIGHT:	x2=x+45;	break;
				case Actor.UP:		y2=y-45; 	break;
				case Actor.DOWN:	y2=y+45;    break;
				}
				
				if (nguyHiem) {
					if (!isBombOrBox(x2, y2, arrBomb, arrBox)) break;
				}

			}
			while((checkViTriAnToan(x2,y2,arrBomb,arrBombBang) && checkViTriDiChuyenDuoc(x2,y2,arrBomb,arrBox)) == false);
		
			if (orient < 5) {
				this.changeOrient(orient);
				this.move(orient, arrBomb, arrBox);
			}
			this.setDem(0);
			
		}else this.setDem(this.getDem()+1);
	}
	
	public void datBomb(ArrayList<Bomb> arrBomb, ArrayList<Box> arrBox, Manager manager) {
		
		if (this.checkToaDo() && random.nextInt(this.getSpeed())==0 
				&& this.getSlBombDaSD() < this.getQuantityBomb()) {
			arrBox.add(0, new Box(x,y,1,"/Images/box1.png"));
			if (checkViTriDiChuyenDuoc(x-45,y, arrBomb, arrBox)) manager.innitBomb(this);
			if (checkViTriDiChuyenDuoc(x+45,y, arrBomb, arrBox)) manager.innitBomb(this);
			if (checkViTriDiChuyenDuoc(x,y-45, arrBomb, arrBox)) manager.innitBomb(this);
			if (checkViTriDiChuyenDuoc(x,y+45, arrBomb, arrBox)) manager.innitBomb(this);
			
			arrBox.remove(0);
		}	
	}
	
}
