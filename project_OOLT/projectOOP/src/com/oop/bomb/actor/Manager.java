package com.oop.bomb.actor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import sound.GameSound;

public class Manager {
	private Random random = new Random();
	private Bomber mBomber;
	private ArrayList<Box> arrBox;
	private ArrayList<Bomb> arrBomb;
	private ArrayList<BombBang> arrBombBang;
	private ArrayList<Monster> arrMonster;
	private ArrayList<Item> arrItem;
	private String Background;
	private int round=1;
	private int nextRound = 0;
	private int status = 0;
	private BufferedReader input;
	private BufferedReader input2;

	public Manager() {
		innitManager();
	}

	public void innitManager() {
		switch (round) {
		case 1:
			mBomber = new Bomber(135, 135, Actor.BOMBER, Actor.DOWN, 250, 1, 1);
			innit("src/Map1/BOX.txt", "src/Map1/MONSTER.txt");
			nextRound = 0;
			status = 0;
			break;
		case 2:
			mBomber.setNew(135, 135);
			mBomber.setSpeed(250);
			mBomber.setQuantityBomb(1);
			mBomber.setSizeBomb(1);
			mBomber.setSlBombDaSD(0);
			innit("src/Map2/BOX.txt", "src/Map2/MONSTER.txt");
			nextRound = 0;
			status = 0;
			break;

		default:
			break;
		}

	}

	public void innit(String pathBox, String pathMonster) {
		arrBox = new ArrayList<Box>();
		arrBomb = new ArrayList<Bomb>();
		arrBombBang = new ArrayList<BombBang>();
		arrMonster = new ArrayList<Monster>();
		arrItem = new ArrayList<Item>();

		innitArrBox(pathBox);
		initarrMonster(pathMonster);
		innitArrItem();

	}

	public void innitArrItem() {

		for (int i = 0; i < arrBox.size(); i++) {
			Box box = arrBox.get(i);
			if (box.getType() == 0) {
				if (random.nextInt(3) == 0) {
					
					Item item = null;
					switch (random.nextInt(3)+1) {
					case 1: item = new Item(box.getX(), box.getY(), 1, "/Images/item_bomb.png");  
							break;
					case 2: item = new Item(box.getX(), box.getY(), 2, "/Images/item_bombsize.png");  
							break;
					case 3: item = new Item(box.getX(), box.getY(), 3, "/Images/item_shoe.png");  
							break;
					case 4: item = new Item(box.getX(), box.getY(), 3, "/Images/item_shoe.png");  
						break;		
					}	
					arrItem.add(item);
				}
			}
			
		}	
	
	}

	public void innitArrBox(String pathBox) {
		try {
			FileReader file = new FileReader(pathBox);
			input = new BufferedReader(file);
			Background = input.readLine();
			String line;
			while ((line = input.readLine()) != null) {
				String str[] = line.split(":");
				int x = Integer.parseInt(str[0]);
				int y = Integer.parseInt(str[1]);
				int type = Integer.parseInt(str[2]);
				String images = str[3];
				Box box = new Box(y*45, x*45, type, images);
				arrBox.add(box);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void innitBomb(Actor actor) {
		if (actor.getStatus() == Actor.DEAD) {
			return;
		}
		int x = actor.getX() + actor.getWidth() / 2;
		int y = actor.getY() + actor.getHeart() / 2;
		for (int i = 0; i < arrBomb.size(); i++) {
			if (arrBomb.get(i).isImpact(x, y)) {
				return;
			}
		}
		if (actor.getSlBombDaSD() >= actor.getQuantityBomb()) {
			return;
		}
		if (actor instanceof Bomber) GameSound.getIstance().getAudio(GameSound.BOMB).play();
		Bomb mBomb = new Bomb(x, y, actor.getSizeBomb(), 2500, actor);
		actor.setSlBombDaSD(actor.getSlBombDaSD()+1);
		arrBomb.add(mBomb);
	}

	public void initarrMonster(String path) {
		try {
			FileReader file = new FileReader(path);
			input2 = new BufferedReader(file);
			String line;
			while ((line = input2.readLine()) != null) {
				String str[] = line.split(":");
				int x = Integer.parseInt(str[0]);
				int y = Integer.parseInt(str[1]);
				int type = Integer.parseInt(str[2]);
				int orient = Integer.parseInt(str[3]);
				int speed = Integer.parseInt(str[4]);
				String images = str[5];
				Monster monster = new Monster(x, y, type, orient, speed, 1, 1,
						images);
				arrMonster.add(monster);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	


	public void drawDialog(Graphics2D g2d, int type) {
		if (type == 0) return;
		g2d.setFont(new Font("Arial", Font.BOLD, 70));
		g2d.setColor(Color.RED);
		if(type==1){
			g2d.drawString("You Lose !!!", 200, 250);
		}else{
			if(type==2){
				g2d.drawString("Round "+round, 200, 250);
			}else{
				g2d.drawString("You Win !!!", 200, 250);
			}
		}
	}

	public void drawAllItem(Graphics2D g2d) {
		for (int i = 0; i < arrItem.size(); i++) {
			arrItem.get(i).drawItem(g2d);
		}
	}

	public void drawAllBox(Graphics2D g2d) {
		for (int i = 0; i < arrBox.size(); i++) {
			arrBox.get(i).drawBox(g2d);
		}
	}

	public void draWBackground(Graphics2D g2d) {
		Image imgBackground = new ImageIcon(getClass().getResource(Background))
				.getImage();
		g2d.drawImage(imgBackground, 0, 0, null);
	}

	public void drawInfo(Graphics2D g2d) {
		Image imgInfor = new ImageIcon(getClass().getResource(
				"/Images/background_Info.png")).getImage();
		g2d.setFont(new Font("Arial", Font.BOLD, 20));
		g2d.setColor(Color.RED);
		g2d.drawImage(imgInfor, 675, 0, null);
		g2d.drawString("HEART", 755, 100);
		Image heart = new ImageIcon(getClass().getResource(
				"/Images/heart_1.png")).getImage();
		if (mBomber.getHeart() == 3) {
			g2d.drawImage(heart, 750, 120, null);
			g2d.drawImage(heart, 775, 120, null);
			g2d.drawImage(heart, 800, 120, null);
		}
		if (mBomber.getHeart() == 2) {
			g2d.drawImage(heart, 760, 120, null);
			g2d.drawImage(heart, 790, 120, null);
		}
		if (mBomber.getHeart() == 1) {
			g2d.drawImage(heart, 775, 120, null);
		}
		g2d.drawString("SCORE : " + mBomber.getScore(), 740, 200);
	}


	public void drawAllBomb(Graphics2D g2d) {
		for (int i = 0; i < arrBomb.size(); i++) {
			arrBomb.get(i).drawActor(g2d);
		}
		for (int i = 0; i < arrBombBang.size(); i++) {
			arrBombBang.get(i).drawBongBang(g2d);
		}
	}

	public void drawAllMonster(Graphics2D g2d) {
		for (int i = 0; i < arrMonster.size(); i++) {
			arrMonster.get(i).drawActor(g2d);
		}
	}

	public void checkWinAndLose() {
		if (mBomber.getHeart() == 0 && nextRound == 0) {
			round = 1;
			status = 1;
			nextRound++;
			GameSound.getIstance().getAudio(GameSound.PLAYGAME).stop();
			GameSound.getIstance().getAudio(GameSound.LOSE).play();
			
		}
		if (arrMonster.size() == 0 && nextRound == 0) {
			if (round == 2) {
				status = 3;
				nextRound++;
				GameSound.getIstance().getAudio(GameSound.PLAYGAME).stop();
				GameSound.getIstance().getAudio(GameSound.WIN).play();				
				round = 1;
				return;
			}
			round = round + 1;
			nextRound++;
			status = 2;
		}
	}

	public void checkDead() {
		for (int i = 0; i < arrBombBang.size(); i++) {
			if (arrBombBang.get(i).isImpactActor(mBomber)  && mBomber.getStatus()==Bomber.ALIVE ) {
				Image icon = new ImageIcon(getClass().getResource(
						"/Images/bomber_dead.png")).getImage();
				mBomber.setImg(icon);
				if (mBomber.getStatus() == Bomber.DEAD) {
					return;
				}
				mBomber.setHeart(mBomber.getHeart() - 1);
				mBomber.setStatus(Bomber.DEAD);
				GameSound.instance.getAudio(GameSound.BOMBER_DIE).play();
			}
		}
		for (int i = 0; i < arrMonster.size(); i++) {
			if (mBomber.isImpactBomberVsActor(arrMonster.get(i))) {
				Image icon = new ImageIcon(getClass().getResource(
						"/Images/ghost.png")).getImage();
				mBomber.setImg(icon);
				if (mBomber.getStatus() == Bomber.DEAD) {
					return;
				}
				mBomber.setHeart(mBomber.getHeart() - 1);
				mBomber.setStatus(Bomber.DEAD);
				GameSound.instance.getAudio(GameSound.BOMBER_DIE).play();
			}
		}
		
	}

	public void checkImpactItem() {
		for (int i = 0; i < arrItem.size(); i++) {
			Item item = arrItem.get(i);
			if (item.isImpactItemVsActor(mBomber)) {	
				GameSound.instance.getAudio(GameSound.ITEM).play();
				switch (item.getType()) {
				case Item.Item_Bomb:
					mBomber.setQuantityBomb(mBomber.getQuantityBomb() + 1);
					arrItem.remove(i);
					break;
				case Item.Item_BombSize:
					mBomber.setSizeBomb(mBomber.getSizeBomb() + 1);
					arrItem.remove(i);
					break;
				case Item.Item_Shoe:
					mBomber.setSpeed(mBomber.getSpeed() - 20);
					arrItem.remove(i);
					break;			
				}
			}
		}
		
		for (int i=0; i<arrMonster.size(); i++) {
			Monster monster = arrMonster.get(i);
			for (int j = 0; j < arrItem.size(); j++) {
				Item item = arrItem.get(j);
				if (item.isImpactItemVsActor(monster)) {		
					switch (item.getType()) {
					case Item.Item_Bomb:
						monster.setQuantityBomb(monster.getQuantityBomb());
						arrItem.remove(j);
						break;
					case Item.Item_BombSize:
						monster.setSizeBomb(monster.getSizeBomb() + 1);
						arrItem.remove(j);
						break;
					case Item.Item_Shoe:
						monster.setSpeed(monster.getSpeed() - 20);
						arrItem.remove(j);
						break;
					
					}
				}
		
			}
		}
	}

	public void deadLineAllBomb() {
		for (int i = 0; i < arrBomb.size(); i++) {
			Bomb bomb = arrBomb.get(i);
			bomb.deadlineBomb();
			if (bomb.getTimeline() == 0) {
				BombBang bomBang = new BombBang(bomb.getX(), bomb.getY(), bomb.getSize(), arrBox);
				GameSound.getIstance().getAudio(GameSound.BONG_BANG).play();
				arrBombBang.add(bomBang);
				
				Actor actor = bomb.getOwner();
				actor.setSlBombDaSD(actor.getSlBombDaSD()-1);
				arrBomb.remove(i);
			}
		}
	
		// Bom no cham bom khac
		for (int i = 0; i < arrBombBang.size(); i++) {
			for (int j = 0; j < arrBomb.size(); j++) {
				if (arrBombBang.get(i).isImpactBomb(arrBomb.get(j))) {
					BombBang bomBang = new BombBang(arrBomb.get(j).getX(),
							arrBomb.get(j).getY(), arrBomb.get(j).getSize(),
							arrBox);
					arrBombBang.add(bomBang);
					Actor actor = arrBomb.get(j).getOwner();
					actor.setSlBombDaSD(actor.getSlBombDaSD()-1);
					arrBomb.remove(j);
				}
			}
		}
		
		//Bom no cham quai
		for (int k = 0; k < arrBombBang.size(); k++) {
			arrBombBang.get(k).deadlineBomb();
			for (int j = 0; j < arrMonster.size(); j++) {
				if (arrBombBang.get(k).isImpactActor(arrMonster.get(j))) { 
						mBomber.setScore(mBomber.getScore() + 1);
						GameSound.getIstance().getAudio(GameSound.MONSTER_DIE).play();
						arrMonster.remove(j);
				}
			}
			if (arrBombBang.get(k).getTimeLine() == 0) {
				arrBombBang.remove(k);
			}
		}
		
		
		for (int i = 0; i < arrBombBang.size(); i++) {
			for (int j = 0; j < arrBox.size(); j++) {
				if (arrBombBang.get(i).isImpactBox(arrBox.get(j))) {
					arrBox.remove(j);	
				}
			}
		}
		
	}


	public void setNewBomb() {
		switch (round) {
		case 1:
			mBomber.setNew(135, 135);
			break;
		case 2:
			mBomber.setNew(135, 135);
			break;
		default:
			break;
		}
	}

	
	public void moveAllMonster() {
		for (int i = 0; i < arrMonster.size(); i++) {
			Monster monster = arrMonster.get(i);
			monster.diChuyen(arrBomb, arrBombBang, arrBox);
			monster.datBomb(arrBomb, arrBox, this);																			
		}
	}

	public ArrayList<Box> getArrBox() {
		return arrBox;
	}

	public ArrayList<Bomb> getArrBomb() {
		return arrBomb;
	}

	public ArrayList<BombBang> getArrBombBang() {
		return arrBombBang;
	}

	public ArrayList<Monster> getArrMonster() {
		return arrMonster;
	}

	public ArrayList<Item> getArrItem() {
		return arrItem;
	}
	
	public Bomber getmBomber() {
		return mBomber;
	}

	public int getStatus() {
		return status;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public int getRound() {
		return round;
	}

	public void setStatus(int status) {
		this.status = status;
	}	
}
