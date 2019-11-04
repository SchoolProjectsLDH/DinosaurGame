package gameComponents;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import zimgHandlers.ImgCompiler;

public class Ground {
	
	public static final int landYPosition = 103;
	
	private final List<LandImg> compiledLands;
	private final BufferedImage land1;
	private final BufferedImage land2;
	private final BufferedImage land3;
	
	private final Player mainCharacter;
	
	public Ground(int width, Player mainCharacter) {
		this.mainCharacter = mainCharacter;
		land1 = ImgCompiler.getResouceImage("data/land1.png");
		land2 = ImgCompiler.getResouceImage("data/land2.png");
		land3 = ImgCompiler.getResouceImage("data/land3.png");
		int numberedLand = width / land1.getWidth() + 2;
		compiledLands = new ArrayList<LandImg>();
		for(int i = 0; i < numberedLand; i++) {
			LandImg imageLand = new LandImg();
			imageLand.positionX = i * land1.getWidth();
			setImageLand(imageLand);
			compiledLands.add(imageLand);
		}
	}
	
	public void updateLandScroll(){
		Iterator<LandImg> itr = compiledLands.iterator();
		LandImg firstElement = itr.next();
		firstElement.positionX -= mainCharacter.getSpeedX();
		float previousPosX = firstElement.positionX;
		while(itr.hasNext()) {
			LandImg element = itr.next();
			element.positionX = previousPosX + land1.getWidth();
			previousPosX = element.positionX;
		}
		if(firstElement.positionX < -land1.getWidth()) {
			compiledLands.remove(firstElement);
			firstElement.positionX = previousPosX + land1.getWidth();
			setImageLand(firstElement);
			compiledLands.add(firstElement);
		}
	}
	
	private void setImageLand(LandImg imgLand) {
		int landType = randLandType();
                switch(landType){
                    case 1:
                        imgLand.image = land1;
                        break;
                    case 2:
                        imgLand.image = land2;
                        break;
                    default:
                        imgLand.image = land3;
                        break;
                }
	}
	
	public void draw(Graphics g) {
		for(LandImg imgLand : compiledLands) {
			g.drawImage(imgLand.image, (int) imgLand.positionX, landYPosition, null);
		}
	}
	
	private int randLandType() {
		Random random = new Random();
		int type = random.nextInt(10);
                switch (type){
                    case 1:
                        return 1;
                    case 9:
                        return 3;
                    default:
                        return 2;
                }
	}
	
	private class LandImg {
		float positionX;
		BufferedImage image;
	}
	
}
