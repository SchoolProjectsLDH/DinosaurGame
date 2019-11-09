package userGUI;

import javax.swing.JFrame;

public class GameCreator extends JFrame {
	
	public static final int SCREEN_WIDTH = 600;//create acctual game window
	private Screen screenPlayer;//new player
        
	
	public GameCreator() {
            super("Chrome Dino Game");//set name
	}
	
	public void startGame(int speed, String UserName) {
            setSize(SCREEN_WIDTH, 175);//size of window
            setLocation(400, 200);//location on screen
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //dont exit program on close
            setResizable(false);//not resizable
            screenPlayer = new Screen(speed, UserName);//update screen with the speed and username
            addKeyListener(screenPlayer);//get key input on screen player
            add(screenPlayer);//place screen
            setVisible(true);//make visible
            screenPlayer.startGame();//start the game
	}
}
