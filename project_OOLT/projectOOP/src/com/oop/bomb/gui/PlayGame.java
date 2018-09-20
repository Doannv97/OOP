package com.oop.bomb.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.BitSet;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.oop.bomb.actor.Bomber;
import com.oop.bomb.actor.Manager;


public class PlayGame extends JPanel implements Runnable,ActionListener{
	public static boolean IS_RUNNING=true;
	private MyContainer mContainer;
	private BitSet traceKey = new BitSet();
	private Manager mMagager = new Manager();
	private int timeDead=0;
	private JButton btn_Menu;
	Thread mytheard;

	public PlayGame(MyContainer mContainer) {
		this.mContainer = mContainer;
		setBackground(Color.WHITE);
		setLayout(null);
		setFocusable(true);
		addKeyListener(keyAdapter);
		innitCompts();
	}
	
	public void startGame() {
			mytheard = new Thread(this);
			mytheard.start(); 	
	}
	
	
	private void innitCompts(){
		btn_Menu = new JButton();
		btn_Menu.setText("Menu");
		btn_Menu.setBounds(750, 520, 100, 30);
		btn_Menu.addActionListener(this);
		add(btn_Menu);
	}
	
	private KeyAdapter keyAdapter = new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			traceKey.set(e.getKeyCode());
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			traceKey.clear(e.getKeyCode());
		}
	};
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new java.awt.BasicStroke(2));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		mMagager.draWBackground(g2d);
		mMagager.drawAllItem(g2d);
		mMagager.drawAllBomb(g2d);
		mMagager.drawAllBox(g2d);
		mMagager.drawAllMonster(g2d);
		mMagager.getmBomber().drawActor(g2d);		
		mMagager.drawInfo(g2d);	
	//	mMagager.drawDialog(g2d, mMagager.getStatus());
		if(mMagager.getStatus()==1){
			mMagager.drawDialog(g2d, 1);
		}
		if(mMagager.getStatus()==2){
			mMagager.drawDialog(g2d, 2);
		}
		if(mMagager.getStatus()==3){
			mMagager.drawDialog(g2d, 3);
		}
	}
	
	@Override
	public void run() {
		while(IS_RUNNING){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			mMagager.getmBomber().diChuyen(traceKey, mMagager.getArrBomb(), mMagager.getArrBox());
			mMagager.getmBomber().datBomb(traceKey, mMagager);
			
			mMagager.moveAllMonster();
								
			mMagager.deadLineAllBomb();
			mMagager.checkDead();
			mMagager.checkImpactItem();
			mMagager.checkWinAndLose();
			
			if(mMagager.getStatus()==1){
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
				IS_RUNNING = false;
				mMagager.innitManager();
				mContainer.setShowMenu();
			}	
			if(mMagager.getStatus()==2){
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				mMagager.innitManager();
			}
			
			if(mMagager.getStatus()==3){
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				IS_RUNNING = false;
				mMagager.innitManager();
				mContainer.setShowMenu();					
					
			}
			
			if(mMagager.getmBomber().getStatus()==Bomber.DEAD){
				timeDead++;
				if(timeDead==2000){
					mMagager.setNewBomb();
					timeDead=0;
				}
			}
						
			repaint();
		
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
/*		if(e.getSource()==btn_Menu){
			mMagager.setRound(1);
			mMagager.setStatus(0);
			IS_RUNNING = false;
			mMagager.innitManager();
			mContainer.setShowMenu();
		}	
*/		
	}

}
