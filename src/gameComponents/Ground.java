package gameComponents;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import zimgHandlers.ImgCompiler;

public class Ground {

    public static final int LAND_Y_POS = 103;//Position of land

    private final List<LandImg> compiledLands;//All land types
    private final BufferedImage land1;//type 1
    private final BufferedImage land2;
    private final BufferedImage land3;//type 3

    private final Player mainCharacter;//player obj

    public Ground(int width, Player mainCharacter) {
        this.mainCharacter = mainCharacter;//update player attributes
        land1 = ImgCompiler.getResouceImage("data/land1.png");//Set land paths
        land2 = ImgCompiler.getResouceImage("data/land2.png");
        land3 = ImgCompiler.getResouceImage("data/land3.png");
        int numberedLand = width / land1.getWidth() + 2;//Start with first
        compiledLands = new ArrayList<LandImg>();//new arraylist of images
        for(int i = 0; i < numberedLand; i++) {//Adding all lands to the list
            LandImg imageLand = new LandImg();
            imageLand.positionX = i * land1.getWidth();
            setImageLand(imageLand);
            compiledLands.add(imageLand);
        }
    }

    public void updateLandScroll(){//Scrolling land
        Iterator<LandImg> itr = compiledLands.iterator();//Get next land type
        LandImg firstElement = itr.next();
        firstElement.positionX -= mainCharacter.getSpeedX();//Change first land based on speed
        float previousPosX = firstElement.positionX;//Last x pos
        while(itr.hasNext()) {//as long as there are images in iterator
            LandImg element = itr.next();//Get a next land image
            element.positionX = previousPosX + land1.getWidth();//set its x pos
            previousPosX = element.positionX;//set it as the new prev x (so they are side by side)
        }
        if(firstElement.positionX < -land1.getWidth()) {//If offscreen
            compiledLands.remove(firstElement);//Delete it
            firstElement.positionX = previousPosX + land1.getWidth();//Move it to the start
            setImageLand(firstElement);//Set its image
            compiledLands.add(firstElement);//Put it back on the list
        }
    }

    private void setImageLand(LandImg imgLand) {//All the different land images
        int landType = randLandType();//get a random type
        switch(landType){//get the image path
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

    public void draw(Graphics g) {//Draw to screen
        for(LandImg imgLand : compiledLands) {//For all images in compiled land, draw them
                g.drawImage(imgLand.image, (int) imgLand.positionX, LAND_Y_POS, null);
        }
    }

    private int randLandType() {//return the land type
        Random random = new Random();//random obj
        int type = random.nextInt(10);//set within numbers of 10
        switch (type){
            case 1://1 in 10 chance of getting scpecial image
                return 1;
            case 9://1 in 10 chance of getting special image
                return 3;
            default://basic image
                return 2;
        }
    }

    private class LandImg {//attributes of landimg
        float positionX;
        BufferedImage image;
    }
	
}
