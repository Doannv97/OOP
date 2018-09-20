package com.oop.bomb.actor;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Item {
	public static final int Item_Bomb=1;
	public static final int Item_BombSize=2;
	public static final int Item_Shoe=3;
	private int x, y, type, width, height;
	private Image img;
	
	public Item(int x, int y, int type, String image) {
		super();
		this.x = x;
		this.y = y;
		this.type = type;
		this.img = new ImageIcon(getClass().getResource(image)).getImage();
		this.width = img.getWidth(null);
		this.height = img.getHeight(null);
	}
	
	public void drawItem(Graphics2D g2d){
		g2d.drawImage(img, x, y, null);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getType() {
		return type;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isImpactItemVsActor(Actor actor){
		Rectangle rec1 = new Rectangle(x, y, width, height);
		Rectangle rec2 = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
		return rec1.intersects(rec2);
	}

}
