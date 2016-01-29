package de.hsh.project.bomberman.game.menu;


import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;


public class LoadingState extends GameState implements Runnable {

	private BufferedImage[] text =null;
	private static final String s = "loading";
	private static int X;
	private static int Y;
	private int ticks = 0;
	private int zahl = 0;
	private static final int SCALE = 5;

	private boolean running;

	public LoadingState() {
		X = this.getPreferredSize().width/2-s.length()/2*SCALE*8;
		Y = this.getPreferredSize().height/2-4*SCALE;
		text = FontState.getText(s);
		running = true;

		new Thread(this).start();
	}


	public void update() {

		int LENGTH = 160;
		ticks++;
		zahl = ticks/20;
		if(ticks >= LENGTH) {
			running = false;
			Game.switchState(new TitleState());
		}

	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0,this.getWidth(), this.getHeight());
		for(int i=0;i<s.length();i++){
			if(i==zahl) {
				g.drawImage(text[i], X+8*(SCALE)*i, Y - 5, 8 * (SCALE + 2), 8 * (SCALE + 2), null);
			}else if(i>zahl){
				g.drawImage(text[i],X+8*(SCALE)*i+16,Y,8*(SCALE),8*(SCALE),null);
			}else{
				g.drawImage(text[i],X+8*(SCALE)*i,Y,8*(SCALE),8*(SCALE),null);
			}
		}
		//g.fillRect(0, 0,this.getWidth(), this.getHeight());
	}

	@Override
	public void run() {
		long start, elapsed, wait;

		while (running) {
			start = System.nanoTime();

			update();
			repaint();

			elapsed = System.nanoTime() - start;
			wait = 1000 / Game.FPS - elapsed / 1000_000;

			try {
				Thread.sleep(wait < 0 ? 5 : wait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}


}
