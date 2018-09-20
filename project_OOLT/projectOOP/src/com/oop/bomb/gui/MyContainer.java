package com.oop.bomb.gui;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JPanel;
import sound.GameSound;

public class MyContainer extends JPanel{
	private static final String TAG_MENU = "tag_menu";
	private static final String TAG_PLAYGAME = "tag_playgame";
	private CardLayout mCardLayout;
	private GUI gui;
	private Menu mMenu;
	private PlayGame mPlayGame;
	
	public MyContainer(GUI mGui){
		this.gui = mGui;
		setBackground(Color.WHITE);
		mCardLayout = new CardLayout();
		setLayout(mCardLayout);
		mMenu = new Menu(this);
		add(mMenu,TAG_MENU);
		mPlayGame = new PlayGame(this);
		add(mPlayGame, TAG_PLAYGAME);
		setShowMenu();

	}

	public GUI getGui() {
		return gui;
	}
	
	public void setShowMenu(){
		mCardLayout.show(MyContainer.this, TAG_MENU);
		mMenu.requestFocus();
		GameSound.getIstance().stop();
		GameSound.getIstance().getAudio(GameSound.MENU).loop();
	}

	public void setShowPlay(){
		PlayGame.IS_RUNNING = true;
		mPlayGame.startGame();
		mCardLayout.show(MyContainer.this, TAG_PLAYGAME);
		mPlayGame.requestFocus();
		GameSound.getIstance().getAudio(GameSound.MENU).stop();
		GameSound.getIstance().getAudio(GameSound.PLAYGAME).loop();
	}	
}
