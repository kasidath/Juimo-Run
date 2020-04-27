package ObjectGame;

import userinterface.GameScreen;
import util.Resources;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemiesManager {
    private List<Enemy> enemies;
    private Random random;

    private BufferedImage imageTrees1,imageTrees2;
    private MainCharacter mainCharacter;
    private GameScreen gameScreen;

    public EnemiesManager(MainCharacter mainCharacter, GameScreen gameScreen){
        this.gameScreen = gameScreen;
        this.mainCharacter = mainCharacter;
        enemies = new ArrayList<Enemy>();
        imageTrees1 = Resources.getResourceImage("data/cactus1.png");
        imageTrees2 = Resources.getResourceImage("data/cactus2.png");
        random = new Random();

        enemies.add(getRandomTrees());

    }
    public void update(){
        for(Enemy e:enemies){
            e.update();
            if(e.isOver() && !e.isScoreGot()){
                gameScreen.plusScore(10);
                e.setIsScoreGot(true);
            }
            if(e.getBound().intersects(mainCharacter.getBound())){
                mainCharacter.setAlive(false);
            }
        }
        Enemy firstEnemy = enemies.get(0);
        if(firstEnemy.isOutOfScreen()){
            enemies.remove(firstEnemy);
            enemies.add(getRandomTrees());
        }
    }
    public void draw(Graphics g){
        for(Enemy e:enemies){
            e.draw(g);
        }
    }
    public void  reset(){
        enemies.clear();
        enemies.add(getRandomTrees());
    }
    private Trees getRandomTrees(){
        Trees trees;
        trees  = new Trees(mainCharacter);
        trees.setX(600);
        if(random.nextBoolean()){
            trees.setImage(imageTrees1);
        }else {
            trees.setImage(imageTrees2);
        }
        return trees;
    }
}
