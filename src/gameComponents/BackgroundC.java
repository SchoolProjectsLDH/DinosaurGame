package gameComponents;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import userGUI.GameCreator;
import zimgHandlers.ImgCompiler;

public class BackgroundC {
	private final List<ImageCloud> listCloud;
	private final BufferedImage cloud;
	
	private final Player mainCharacter;
	
	public BackgroundC(int width, Player mainCharacter) {
		this.mainCharacter = mainCharacter;
		cloud = ImgCompiler.getResouceImage("data/cloud.png");
		listCloud = new ArrayList<ImageCloud>();
		
		ImageCloud imageCloud = new ImageCloud();
		imageCloud.positionX = 0;
		imageCloud.positionY = 30;
		listCloud.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.positionX = 150;
		imageCloud.positionY = 40;
		listCloud.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.positionX = 300;
		imageCloud.positionY = 50;
		listCloud.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.positionX = 450;
		imageCloud.positionY = 20;
		listCloud.add(imageCloud);
		
		imageCloud = new ImageCloud();
		imageCloud.positionX = 600;
		imageCloud.positionY = 60;
		listCloud.add(imageCloud);
	}
	
	public void moveCloud(){
		Iterator<ImageCloud> itr = listCloud.iterator();
		ImageCloud firstElement = itr.next();
		firstElement.positionX -= mainCharacter.getSpeedX()/8;
		while(itr.hasNext()) {
			ImageCloud element = itr.next();
			element.positionX -= mainCharacter.getSpeedX()/8;
		}
		if(firstElement.positionX < -cloud.getWidth()) {
			listCloud.remove(firstElement);
			firstElement.positionX = GameCreator.SCREEN_WIDTH;
			listCloud.add(firstElement);
		}
	}
	
	public void drawNewCloudLoc(Graphics g) {
		for(ImageCloud imgLand : listCloud) {
			g.drawImage(cloud, (int) imgLand.positionX, imgLand.positionY, null);
		}
	}
	
	private class ImageCloud {
		float positionX;
		int positionY;
	}
}
