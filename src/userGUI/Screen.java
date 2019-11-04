package userGUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import gameComponents.BackgroundC;
import gameComponents.Ground;
import gameComponents.Player;
import zimgHandlers.ImgCompiler;

public class Screen extends JPanel implements Runnable, KeyListener {
    
	private final int gameStart = 0;
	private final int gamePlaying = 1;
	private final int gameOver = 2;
	
	private final Ground land;
	private final Player mainCharacter;//scorestuff
	private final BackgroundC clouds;
	private Thread thread;

	private boolean isKeyPressed;

	private int gameState = gameStart;

	private final BufferedImage replayButtonImage;
	private final BufferedImage gameOverButtonImage;
        double fps = 100;
	double msPerFrame = 1000 * 1000000 / fps;
        
	public Screen(int difficulty) {
		mainCharacter = new Player();
		land = new Ground(GameCreator.SCREEN_WIDTH, mainCharacter);
		mainCharacter.setSpeedX(difficulty);
		replayButtonImage = ImgCompiler.getResouceImage("data/replay_button.png");
		gameOverButtonImage = ImgCompiler.getResouceImage("data/gameover_text.png");
		clouds = new BackgroundC(GameCreator.SCREEN_WIDTH, mainCharacter);
	}

	public void startGame() {
		thread = new Thread(this);
		thread.start();
	}

	public void gameUpdate() {
		if (gameState == gamePlaying) {
			clouds.moveCloud();
			land.updateLandScroll();
			mainCharacter.updatePlayerState();
		}
	}

        @Override
	public void paint(Graphics g) {
		g.setColor(Color.decode("#f7f7f7"));
		g.fillRect(0, 0, getWidth(), getHeight());

		switch (gameState) {
		case gameStart:
			mainCharacter.drawPlayerComponents(g);
			break;
		case gamePlaying:
                    	clouds.drawNewCloudLoc(g);
			land.draw(g);
			mainCharacter.drawPlayerComponents(g);
			g.setColor(Color.BLACK);
			g.drawString("Score: " + mainCharacter.score, 500, 20);
                        break;
		case gameOver:
			g.drawImage(gameOverButtonImage, 200, 30, null);
			break;
		}
	}

	@Override
	public void run() {

		double lastTime = 0;
		double elapsed;
		
		int msSleep;
		int nanoSleep;

		long endProcessGame;
		long lag = 0;

		while (true) {
                        fps += 0.001;
                        msPerFrame = 1000 * 1000000 / fps;
			gameUpdate();
			repaint();
			endProcessGame = System.nanoTime();
			elapsed = (lastTime + msPerFrame - System.nanoTime());
			msSleep = (int) (elapsed / 1000000);
			nanoSleep = (int) (elapsed % 1000000);
			if (msSleep <= 0) {
				lastTime = System.nanoTime();
				continue;
			}
			try {
				Thread.sleep(msSleep, nanoSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lastTime = System.nanoTime();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!isKeyPressed) {
			isKeyPressed = true;
			switch (gameState) {
			case gameStart:
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					gameState = gamePlaying;
				}
				break;
			case gamePlaying:
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					mainCharacter.jumpArcMaker();
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					mainCharacter.crouchPersist(true);
				}
				break;
			case gameOver:
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					gameState = gamePlaying;
					resetGame();
				}
				break;

			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		isKeyPressed = false;
		if (gameState == gamePlaying) {
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				mainCharacter.crouchPersist(false);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	private void resetGame() {
		mainCharacter.playerDeadState(false);
		mainCharacter.returnToLand();
                fps = 100;
                mainCharacter.score = 0;

	}

}
