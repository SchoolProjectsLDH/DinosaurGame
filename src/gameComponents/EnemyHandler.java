package gameComponents;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import zimgHandlers.ImgCompiler;

public class EnemyHandler {

    private final BufferedImage cactus1;//Cactus image
    private final BufferedImage cactus2;//Cactus 2 image
    private final BufferedImage bird;//Birg image
    private final Random random;//Enemy randomizer

    private final List<Enemy> enemies;//List of the enemies
    private final Player mainCharacter;//Player obj

    public EnemyHandler(Player mainCharacter) {
        random = new Random();//Randomizer
        cactus1 = ImgCompiler.getResouceImage("data/cactus1.png");//Set image path
        cactus2 = ImgCompiler.getResouceImage("data/cactus2.png");//Set image path
        bird = ImgCompiler.getResouceImage("data/bird.png");//Set image path
        enemies = new ArrayList<Enemy>();//Array of possible enemies
        this.mainCharacter = mainCharacter;//Update player
        enemies.add(enemyCreator());//Create enemy to screen
    }

    public void updateEnemyLocations() {//Sending enemies twrd player
	for(Enemy en : enemies) {//Itr through enemies
            en.updateBaddieLoc();//update all of them
        }
	Enemy enemy = enemies.get(0);//get most recent enemy
	if(enemy.offScreen()) {//If its offscreen
            mainCharacter.incrementScore();//Means the player got over it. add 20
            enemies.clear();//delete it
            enemies.add(enemyCreator());//make a new enemy
	}
    }

    public void draw(Graphics g) {//Drawing the enemies to their locations
	for(Enemy e : enemies) {
            e.drawLocation(g);
        }
    }

    private Enemy enemyCreator() {
	int type = random.nextInt(4);//Randomize type of enemy
        switch(type){
            case 0://New small cactus
                return new Baddie(mainCharacter, 800, cactus1.getWidth() - 10, cactus1.getHeight() - 10, cactus1);
            case 1://Large cactus
                return new Baddie(mainCharacter, 800, cactus2.getWidth() - 10, cactus2.getHeight() - 10, cactus2);
            case 3://Low bird
                return new Baddie(mainCharacter, 800, bird.getWidth() - 10, bird.getHeight() - 10, bird);
            default://High bird
                return new BigBird(mainCharacter, 800, bird.getWidth() - 10, bird.getHeight() - 10, bird);
        }
    }

    public boolean isCollided() {//Check if hitboxes colided
        for(Enemy e : enemies) {//Checking all enemy types
            if (mainCharacter.getHitBox().intersects(e.getHitbox())) {//If the bounds intersect, return true
                return true;
            }
        }
	return false;//None of them intersect
    }

    public void refreshEnemy() {//Recycle enemies
        enemies.clear();//Delete
        enemies.add(enemyCreator());//Create
    }

}
