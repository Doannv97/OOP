package com.oop.bomb.actor;

import java.awt.Rectangle;
import java.util.ArrayList;

public class Weapons {
	protected ArrayList<Rectangle> scope = new ArrayList<Rectangle>();
	
	public boolean isBang(int x, int y, int width, int height) {
		Rectangle rec = new Rectangle(x,y,width,height);
		for (int i = 0; i < scope.size(); i++) {
			if (scope.get(i).intersects(rec)) return true;
		}
		return false;
	}
	
	public boolean isImpactActor(Actor actor){
		Rectangle rec = new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
		for (int i = 0; i < scope.size(); i++) {
			if (scope.get(i).intersects(rec)) return true;
		}
		return false;
	}
	
	public boolean isImpactBomb(Bomb bomb){
		Rectangle rec = new Rectangle(bomb.getX(), bomb.getY(), bomb.getWidth(), bomb.getHeight());
		for (int i = 0; i < scope.size(); i++) {
			if (scope.get(i).intersects(rec)) return true;
		}
		return false;
	}
	
	public boolean isImpactBox(Box box){
		if(box.getType()==Box.DISALLROW_BANG){
			return false;
		}
		Rectangle rec = new Rectangle(box.getX(), box.getY(), box.getWidth(), box.getHeight());
		for (int i = 0; i < scope.size(); i++) {
			if (scope.get(i).intersects(rec)) return true;
		}
		return false;
	}
	
	
}
