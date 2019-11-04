package gameComponents;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import zimgHandlers.ImgCompiler;

public class EnemyHandler {

    private final BufferedImage cactus1;
    private final BufferedImage cactus2;
    private final BufferedImage bird;
    private final Random random;

    private final List<Enemy> enemies;
    private final Player mainCharacter;

    public EnemyHandler(Player mainCharacter) {
        random = new Random();
        cactus1 = ImgCompiler.getResouceImage("data/cactus1.png");
        cactus2 = ImgCompiler.getResouceImage("data/cactus2.png");
        bird = ImgCompiler.getResouceImage("data/bird.png");
        enemies = new ArrayList<Enemy>();
        this.mainCharacter = mainCharacter;
        enemies.add(enemyCreator());
    }

    public void updateEnemyLocations() {
	for(Enemy en : enemies) {
            en.updateBaddieLoc();
        }
	Enemy enemy = enemies.get(0);
	if(enemy.offScreen()) {
            mainCharacter.incrementScore();
            enemies.clear();
            enemies.add(enemyCreator());
	}
    }

    public void draw(Graphics g) {
	for(Enemy e : enemies) {
            e.drawLocation(g);
        }
    }

    private Enemy enemyCreator() {
	int type = random.nextInt(4);
        switch(type){
            case 0:
                return new Baddie(mainCharacter, 800, cactus1.getWidth() - 10, cactus1.getHeight() - 10, cactus1);
            case 1:
                return new Baddie(mainCharacter, 800, cactus2.getWidth() - 10, cactus2.getHeight() - 10, cactus2);
            case 3:
                return new Baddie(mainCharacter, 800, bird.getWidth() - 10, bird.getHeight() - 10, bird);
            default:
                return new BaddieBird(mainCharacter, 800, bird.getWidth() - 10, bird.getHeight() - 10, bird);
        }
    }

    public boolean isCollided() {
        for(Enemy e : enemies) {
            if (mainCharacter.getHitBox().intersects(e.getHitbox())) {
                return true;
            }
        }
	return false;
    }

    public void refreshEnemy() {
        enemies.clear();
        enemies.add(enemyCreator());
    }

}
