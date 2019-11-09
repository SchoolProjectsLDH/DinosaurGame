package gameComponents;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Baddie extends Enemy {

    public final int basePos = 125;//all have the same ground position

    private int positionX;//For scrolling
    private final int baddieWidth;//Width (For hitbox)
    private final int baddieHeight;//Height

    private final BufferedImage image;//Images of all the bad guys
    private final Player mainCharacter;//Player obj

    private Rectangle rectBound;//Hitbox rectangle

    public Baddie(Player mainCharacter, int posX, int width, int height, BufferedImage image) {
        this.positionX = posX;//Init baddie with attributes
        this.baddieWidth = width;
        this.baddieHeight = height;
        this.image = image;
        this.mainCharacter = mainCharacter;
        rectBound = new Rectangle();
    }

    @Override
    public void updateBaddieLoc() {//Move baddie twrd player
        positionX -= mainCharacter.getSpeedX();//Move its position depending on the speed
    }

    @Override
    public void drawLocation(Graphics g) {//Update image locations
        g.drawImage(image, positionX, basePos - image.getHeight(), null);//send image to screen 
    }

    @Override
    public Rectangle getHitbox() {//Get the bounds of the enemy on screen
        rectBound = new Rectangle();//New hitbox reader
        rectBound.x = (int) positionX + (image.getWidth() - baddieWidth)/2;//Get x bounds
        rectBound.y = basePos - image.getHeight() + (image.getHeight() - baddieHeight)/2;//Get y bounds
        rectBound.width = baddieWidth;
        rectBound.height = baddieHeight;
        return rectBound;//return a bound object
    }

    @Override
    public boolean offScreen() {//If offscreen
        return positionX < -image.getWidth();//Baddie has negative position (off window)
    }
	
}
