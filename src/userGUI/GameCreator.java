package userGUI;

import javax.swing.JFrame;

public class GameCreator extends JFrame {
	
	public static final int SCREEN_WIDTH = 600;
	private Screen screenPlayer;
	
	public GameCreator() {
            super("T-Rex game");
	}
	
	public void startGame(int speed) {
            setSize(SCREEN_WIDTH, 175);
            setLocation(400, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);
            screenPlayer = new Screen(speed);
            addKeyListener(screenPlayer);
            add(screenPlayer);
            setVisible(true);
            screenPlayer.startGame();
	}
	
//	public static void main(String args[]) {
//		(new GameWindow()).startGame();
//	}
}
