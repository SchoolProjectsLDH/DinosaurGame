package gameComponents;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import userGUI.GameCreator;
import zimgHandlers.ImgCompiler;

public class BackgroundC {
	private final List<ImageCloud> listCloud;//Cloud types
	private final BufferedImage cloud;//Cloud Buffer
	
	private final Player mainCharacter;//Character object
	
	public BackgroundC(int width, Player mainCharacter) {//initialize cloud locations
		this.mainCharacter = mainCharacter;//transfer mainCharacter data
		cloud = ImgCompiler.getResouceImage("data/cloud.png");//Cloud image
		listCloud = new ArrayList<ImageCloud>();
		
		ImageCloud imageCloud = new ImageCloud();//new cloud
		imageCloud.positionX = 0;//Give this some location on the screen
		imageCloud.positionY = 30;
		listCloud.add(imageCloud);//Add to main listcloud
		
		imageCloud = new ImageCloud();//Comments above
		imageCloud.positionX = 150;
		imageCloud.positionY = 40;
		listCloud.add(imageCloud);
		
		imageCloud = new ImageCloud();//Comments above
		imageCloud.positionX = 300;
		imageCloud.positionY = 50;
		listCloud.add(imageCloud);
		
		imageCloud = new ImageCloud();//Comments above
		imageCloud.positionX = 450;
		imageCloud.positionY = 20;
		listCloud.add(imageCloud);
		
		imageCloud = new ImageCloud();//Comments above
		imageCloud.positionX = 600;
		imageCloud.positionY = 60;
		listCloud.add(imageCloud);
	}
	
	public void moveCloud(){//Scroll clouds twrd player
		Iterator<ImageCloud> itr = listCloud.iterator();//switch between cloud locations
		ImageCloud firstElement = itr.next();//get the next location
		firstElement.positionX -= mainCharacter.getSpeedX()/8;//depending on speed move position 1/8th of that forward
		while(itr.hasNext()) {//If there is a next cloud create it at the beginning
			ImageCloud element = itr.next();
			element.positionX -= mainCharacter.getSpeedX()/8;
		}
		if(firstElement.positionX < -cloud.getWidth()) {//If the cloud is offscreen
			listCloud.remove(firstElement);//Delete it
			firstElement.positionX = GameCreator.SCREEN_WIDTH;//Reset its x position
			listCloud.add(firstElement);//Add it back to the screen
		}
	}
	
	public void drawNewCloudLoc(Graphics g) {
		for(ImageCloud imgLand : listCloud) {//Draw the clouds
			g.drawImage(cloud, (int) imgLand.positionX, imgLand.positionY, null);//Draw cloud image to x y coords
		}
	}
	
	private class ImageCloud {//Attributes of imagecloud
		float positionX;
		int positionY;
	}
}
