package gameComponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Baddie extends Enemy {
	
	public final int basePos = 125;
	
	private int positionX;
	private final int baddieWidth;
	private final int baddieHeight;
	
	private final BufferedImage image;
	private final Player mainCharacter;
	
	private Rectangle rectBound;
	
	public Baddie(Player mainCharacter, int posX, int width, int height, BufferedImage image) {
		this.positionX = posX;
		this.baddieWidth = width;
		this.baddieHeight = height;
		this.image = image;
		this.mainCharacter = mainCharacter;
		rectBound = new Rectangle();
	}
	
        @Override
	public void updateBaddieLoc() {
		positionX -= mainCharacter.getSpeedX();
	}
	
        @Override
	public void drawLocation(Graphics g) {
		g.drawImage(image, positionX, basePos - image.getHeight(), null);
		g.setColor(Color.red);
	}
	
        @Override
	public Rectangle getHitbox() {
		rectBound = new Rectangle();
		rectBound.x = (int) positionX + (image.getWidth() - baddieWidth)/2;
		rectBound.y = basePos - image.getHeight() + (image.getHeight() - baddieHeight)/2;
		rectBound.width = baddieWidth;
		rectBound.height = baddieHeight;
		return rectBound;
	}

	@Override
	public boolean offScreen() {
                return positionX < -image.getWidth();
	}
	
}
