package com.oop.bomb.actor;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class BombBang extends Weapons{
	private int x,y,size,timeLine;
	private Image img;
	private int left, right, up, down;
	
	public BombBang(int x, int y, int size, ArrayList<Box> arrBox) {
		this.x=x;
		this.y=y;
		this.size=size;
		this.timeLine = 250;
		
		left = size;
		right = size;
		up = size;
		down = size;
		
		scope.add(new Rectangle(x, y, 45, 45));
		//LEFT
		for(int i=1; i<=size; i++){
			left--;
			Rectangle rec = new Rectangle(x-(i)*45, y, 45, 45);
			scope.add(rec);
			if (isBox(x-(i)*45, y, 45, 45, arrBox))  break;			
		}
	
		//RIGHT
		for(int i=1;i<=size;i++){
			right--;
			Rectangle rec = new Rectangle(x + i*45, y, 45, 45);
			scope.add(rec);
			if (isBox(x+(i)*45, y, 45, 45, arrBox)) break;
		}
			
		//UP
		for(int i=1;i<=size;i++){
			up--;
			Rectangle rec = new Rectangle(x, y-(i*45), 45, 45);
			scope.add(rec);
			if (isBox(x, y-(i*45), 45, 45, arrBox)) break;
		}		
		
		//DOWN
		for(int i=1;i<=size;i++){
			down--;
			Rectangle rec = new Rectangle(x, y+i*45, 45, 45);
			scope.add(rec);
			if (isBox(x, y+ i*45, 45, 45, arrBox)) break;
		}		
	}
	
	public boolean isBox(int x, int y, int width, int height, ArrayList<Box> arrBox){
		if (x < 0 || y < 0 || x > 630 || y > 540) return true;
		Rectangle rec1 = new Rectangle(x, y, width, height);
		for(int i=0;i<arrBox.size();i++){
			Box box = arrBox.get(i);
			Rectangle rec2 = new Rectangle(box.getX(), box.getY(), box.getWidth(), box.getHeight());
			if (rec1.intersects(rec2))
				return true;
		}
		return false;
	}
	
	
	public void drawBongBang(Graphics2D g2d){
		switch (this.size) {
		case 1: img = new ImageIcon(getClass().getResource("/Images/bombbang1.png")).getImage();
				break;
		case 2: img = new ImageIcon(getClass().getResource("/Images/bombbang2.png")).getImage();
				break;
		case 3: img = new ImageIcon(getClass().getResource("/Images/bombbang3.png")).getImage();
				break;
		case 4: img = new ImageIcon(getClass().getResource("/Images/bombbang4.png")).getImage();
				break;
		case 5: img = new ImageIcon(getClass().getResource("/Images/bombbang5.png")).getImage();
				break;
		case 6: img = new ImageIcon(getClass().getResource("/Images/bombbang6.png")).getImage();
				break;
		default:
				img = new ImageIcon(getClass().getResource("/Images/bombbang7.png")).getImage();
				break;
		}
		
		g2d.drawImage(img, x - 45*(size-left), y - 45*(size-up), x + 45*(size+1-right), y + 45*(size+1-down), 
				0 + 45*left, 0 + 45*up, img.getWidth(null) - 45*right, img.getHeight(null) - 45*down, null);
		
	}
	
	
	public void deadlineBomb(){
		if(timeLine>0){
			timeLine--;
		}
	}

	public int getTimeLine() {
		return timeLine;
	}
	
	
}
