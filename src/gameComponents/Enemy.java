package gameComponents;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Enemy {
	public abstract void updateBaddieLoc();
	public abstract void drawLocation(Graphics g);
	public abstract Rectangle getHitbox();
	public abstract boolean offScreen();
}
