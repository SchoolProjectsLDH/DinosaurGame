package userGUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import gameComponents.*;

public class Screen extends JPanel implements Runnable, KeyListener {

    private final int gameStart = 0;//start state
    private final int gamePlaying = 1;//playing state
    private final int gameOver = 2;//game over state

    private final Ground land;//new land
    private final Player mainCharacter;//new player
    private final EnemyHandler enemiesHandler;//new handler
    private final BackgroundC clouds;//cloud 
    private Thread thread;//main game thread

    private boolean isKeyPressed;

    private int gameState = gameStart;

    protected double fps = 100;
    protected double msPerFrame = 1000 * 1000000 / fps;
    protected String UserName;

    public Screen(int difficulty, String UserName) {
        this.UserName = UserName;//set username
        mainCharacter = new Player();//new player obj
        land = new Ground(GameCreator.SCREEN_WIDTH, mainCharacter);//create the ground
        mainCharacter.setSpeedX(difficulty);//set the speed of game
        enemiesHandler = new EnemyHandler(mainCharacter);//start enemy handling with character attributes
        clouds = new BackgroundC(GameCreator.SCREEN_WIDTH, mainCharacter);//new clouds scrolling
    }

    public void startGame() {
        thread = new Thread(this);//new game thread
        thread.start();
    }

    public void gameUpdate() {//game update checker
        if (gameState == gamePlaying) {//if still playing
            clouds.moveCloud();//scroll cloud
            land.updateLandScroll();//scroll land
            mainCharacter.updatePlayerState();//check player state
            enemiesHandler.updateEnemyLocations();//scroll enemy
            if (enemiesHandler.isCollided()) {//if player and enemy intersect
                mainCharacter.playDeadOof();//play dead sound
                gameState = gameOver;//game over state
                mainCharacter.playerDeadState(true);//is dead true
            }
        }
    }

    @Override
    public void paint(Graphics g) {//painting components
        g.setColor(Color.decode("#f7f7f7"));
        g.fillRect(0, 0, getWidth(), getHeight());

        switch (gameState) {
            case gameStart://on start
                mainCharacter.drawPlayerComponents(g);//draw player
                break;
            case gamePlaying://on playing
                clouds.drawNewCloudLoc(g);//cycle cloud scrolling
                land.draw(g);//land scrolling
                enemiesHandler.draw(g);//draw enemy
                mainCharacter.drawPlayerComponents(g);//draw player
                g.setColor(Color.BLACK);
                g.drawString("Score: " + mainCharacter.score, 500, 20);//place score label
                break;
            case gameOver://game end
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        new Lose(mainCharacter.score, UserName).setVisible(true);//open lose window
                    }
                });
                break;
        }
    }

    @Override
    public void run() {//main thread

        double lastTime = 0;//various times for speed scaling
        double elapsed;

        int msSleep;
        int nanoSleep;

        long endProcessGame;
        long lag = 0;

        while (gameState!= gameOver) {//while you arent dead
            fps += 0.001;//increment speed
            msPerFrame = 1000 * 1000000 / fps;//shorten ms per frame
            gameUpdate();//update all game assets
            repaint();//repaint the game assets
            endProcessGame = System.nanoTime();//time for measuring ticks
            elapsed = (lastTime + msPerFrame - System.nanoTime());
            msSleep = (int) (elapsed / 1000000);
            nanoSleep = (int) (elapsed % 1000000);//sleeping for some time to create the illusion of speed
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
    public void keyPressed(KeyEvent e) {//depending on key press
        if (!isKeyPressed) {
            isKeyPressed = true;
            switch (gameState) {
                case gameStart://game not started yet
                    if (e.getKeyCode() == KeyEvent.VK_UP) {//key is up
                        gameState = gamePlaying;// start game
                    }
                    break;
                case gamePlaying://game started
                    if (e.getKeyCode() == KeyEvent.VK_UP) {//key is up
                        mainCharacter.jumpArcMaker();//jump
                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {//key is down
                        mainCharacter.crouchPersist(true);//crouch
                    }
                    break;
                case gameOver://game is over
                    if (e.getKeyCode() == KeyEvent.VK_UP) {//key up
                        gameState = gamePlaying;//play again
                        resetGame();
                    }
                    break;

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        isKeyPressed = false;//no key pressed
        if (gameState == gamePlaying) {//and still playing
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {//stop being crouched
                mainCharacter.crouchPersist(false);//go back to normal run
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    private void resetGame() {//reinit variables
        mainCharacter.playerDeadState(false);
        mainCharacter.returnToLand();
        enemiesHandler.refreshEnemy();
        fps = 100;
        mainCharacter.score = 0;

    }

}
