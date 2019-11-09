package gameComponents;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import zimgHandlers.Animator;
import zimgHandlers.ImgCompiler;

public class Player {

    public static final int LAND_Y_POS = 80;
    public static final float GRAVITY = 0.4f;

    private static final int NORMAL_RUN = 0;//running state
    private static final int JUMP = 1;//jumping state
    private static final int CROUCH = 2;
    private static final int DEAD = 3;

    private float positionY;
    private float positionX;
    private float speedX;
    private float speedY;
    private Rectangle hitbox;
    
    public int score = 0;//Score attribute

    private int state = NORMAL_RUN;//initial state

    private Animator runAnimation;
    private BufferedImage jumpAnimation;
    private Animator crouchAnimation;
    private BufferedImage deathStillPic;

    private AudioClip jSound;//Audio file for jumping
    private AudioClip dSound;//Audio for dying
    private AudioClip scoreHundredSound;//If audio factor of 100

    public Player() {//Init all of maincharacter
        positionX = 50;//always at the same x coord
        positionY = LAND_Y_POS;//stands on landY
        hitbox = new Rectangle();//set hitbox obj
        runAnimation = new Animator(90);//Animates run animation
        runAnimation.addFrame(ImgCompiler.getResouceImage("data/main-character1.png"));//Add frames of run
        runAnimation.addFrame(ImgCompiler.getResouceImage("data/main-character2.png"));
        jumpAnimation = ImgCompiler.getResouceImage("data/main-character3.png");
        crouchAnimation = new Animator(90);
        crouchAnimation.addFrame(ImgCompiler.getResouceImage("data/main-character5.png"));//Add frames of crouch
        crouchAnimation.addFrame(ImgCompiler.getResouceImage("data/main-character6.png"));
        deathStillPic = ImgCompiler.getResouceImage("data/main-character4.png");

        try {
            jSound =  Applet.newAudioClip(new URL("file","","data/jump.wav"));//get wav file
            dSound =  Applet.newAudioClip(new URL("file","","data/dead.wav"));
            scoreHundredSound =  Applet.newAudioClip(new URL("file","","data/scoreup.wav"));
        } catch (MalformedURLException e) {
            e.printStackTrace();//print error if url not found
        }
    }

    public float getSpeedX() {//speed getter
        return speedX;
    }

    public void setSpeedX(int speedX) {//speed setter
        this.speedX = speedX;
    }

    public void drawPlayerComponents(Graphics g) {//drawing player based on state
        switch(state) {
            case NORMAL_RUN://If running
                g.drawImage(runAnimation.getFrame(), (int) positionX, (int) positionY, null);
                break;
            case JUMP://if jumping
                g.drawImage(jumpAnimation, (int) positionX, (int) positionY, null);
                break;
            case CROUCH://crouching
                g.drawImage(crouchAnimation.getFrame(), (int) positionX, (int) (positionY + 20), null);
                break;
            case DEAD://dead
                g.drawImage(deathStillPic, (int) positionX, (int) positionY, null);
                break;
        }
    }

    public void updatePlayerState() {
        runAnimation.updateFrame();//Go to next animation frame
        crouchAnimation.updateFrame();//next crouch frame
        if(positionY >= LAND_Y_POS) {//if in the sky
            positionY = LAND_Y_POS;//have it bring you back eventually
            if(state != CROUCH) {//If you arent crouching when you land
                state = NORMAL_RUN;//you must be running
            }
        } else {
            speedY += GRAVITY;//set jump arc factor
            positionY += speedY;
        }
    }

    public void jumpArcMaker() {
        if(positionY >= LAND_Y_POS) {//if you jump
            if(jSound != null) {//play jump sound
                jSound.play();
            }
            speedY = -7.5f;//go back down twrd ground
            positionY += speedY;//move you up
            state = JUMP;//you are jumping
        }
    }

    public void crouchPersist(boolean isDown) {
        if(state == JUMP) {//if you are jumping
            return;//ignore
        }
        if(isDown) {//if key is down
            state = CROUCH;//keep crouching
        } else {
            state = NORMAL_RUN;//go back to running
        }
    }

    public Rectangle getHitBox() {//Get hitbox bounds
        hitbox = new Rectangle();
        if(state == CROUCH) {//Get bounds of crouch image
            hitbox.x = (int) positionX + 5;
            hitbox.y = (int) positionY + 20;
            hitbox.width = crouchAnimation.getFrame().getWidth() - 10;
            hitbox.height = crouchAnimation.getFrame().getHeight();
        } else {//get bounds of run image
            hitbox.x = (int) positionX + 5;
            hitbox.y = (int) positionY;
            hitbox.width = runAnimation.getFrame().getWidth() - 10;
            hitbox.height = runAnimation.getFrame().getHeight();
        }
        return hitbox;//give back hitbox coords as rectangle obj
    }

    public void playerDeadState(boolean isDeath) {
        if(isDeath) {
            state = DEAD;//set dead
        } else {
            state = NORMAL_RUN;//if not, keep running
        }
    }

    public void returnToLand() {//reset y pos
        positionY = LAND_Y_POS;
    }

    public void playDeadOof() {//play death sound
        dSound.play();
    }

    public void incrementScore() {//add to score
        score += 20;
        if(score % 100 == 0) {//if its a factor of 100 play the chime sound
            scoreHundredSound.play();
        }
    }
        	
}
