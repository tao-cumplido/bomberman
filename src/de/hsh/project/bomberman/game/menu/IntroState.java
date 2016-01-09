package de.hsh.project.bomberman.game.menu;


import de.hsh.project.bomberman.game.Game;
import de.hsh.project.bomberman.game.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class IntroState extends GameState implements Runnable {

	private BufferedImage logo;
	
	private int alpha;
	private int ticks;
	private boolean running;
	
	public IntroState() {
		running = true;
		ticks = 0;
		try {
			logo = ImageIO.read(getClass().getResourceAsStream("/res/images/logo.png"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				super.keyPressed(e);
				if(e.getKeyCode()==KeyEvent.VK_ENTER){
					running = false;
					Game.switchState(new LoadingState());
				}
			}
		});

		new Thread(this).start();
	}


	public void update() {
		int FADE_IN = 60;
		int LENGTH = 60;
		final int FADE_OUT = 60;
		ticks++;
		if(ticks < FADE_IN) {
			alpha = (int) (255 - 255 * (1.0 * ticks / FADE_IN));
			if(alpha < 0) alpha = 0;
		}
		if(ticks > FADE_IN + LENGTH) {
			alpha = (int) (255 * (1.0 * ticks - FADE_IN - LENGTH) / FADE_OUT);
			if(alpha > 255) alpha = 255;
		}
		if(ticks > FADE_IN + LENGTH + FADE_OUT) {
			running = false;
			Game.switchState(new LoadingState());

		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0,this.getWidth(), this.getHeight());
		g.drawImage(logo, 0, 0, this.getWidth(), this.getHeight(), null);
		g.setColor(new Color(0, 0, 0, alpha));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
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
