package userinterface;

import ObjectGame.*;
import util.Resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class GameScreen extends JPanel implements Runnable, KeyListener {
    public static final int GAME_FIRST_STATE = 0;
    public static final int GAME_PLAY_STATE = 1;
    public static final int GAME_OVER_STATE = 2;
    public static final float GRAVITY = 0.1f;
    public static final float GROUNDY = 130;

    private MainCharacter mainCharacter;
    private Thread thread;
    private Land land;
    private Clounds clounds;
    private EnemiesManager enemiesManager;
    private int score;

    private int gameState = GAME_FIRST_STATE;
    private BufferedImage imageGameOverText;

    public GameScreen(){
        thread = new Thread(this);
        mainCharacter = new MainCharacter();
        mainCharacter.setX(50);
        land = new Land(this);
        clounds = new Clounds();
        enemiesManager = new EnemiesManager(mainCharacter,this);
        imageGameOverText = Resources.getResourceImage("data/gameover_text.png");

    }
    public void startGame(){
        thread.start();
    }
    public void run() {
        while (true){
            //System.out.println(i++);
            try {
                update();
                repaint();
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public void update(){
        switch (gameState){
            case GAME_PLAY_STATE:
                land.update();
                mainCharacter.update();
                clounds.update();
                enemiesManager.update();
                if(!mainCharacter.getAlive())
                    gameState = GAME_OVER_STATE;
                break;
        }

    }
    public void plusScore(int score){
        this.score += score;
    }
    public void paint(Graphics g){
        g.setColor(Color.decode("#a0e9f3"));
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.BLACK);
      //  g.drawLine(0,(int)GROUNDY,getWidth(),(int)GROUNDY);

        switch (gameState){
            case GAME_FIRST_STATE:
                mainCharacter.draw(g);
                break;
            case GAME_PLAY_STATE:
                clounds.draw(g);
                land.draw(g);
                mainCharacter.draw(g);
                enemiesManager.draw(g);
                g.drawString("HI "+String.valueOf(score), 500,30);
                break;
            case GAME_OVER_STATE:
                clounds.draw(g);
                land.draw(g);
                mainCharacter.draw(g);
                enemiesManager.draw(g);
                g.drawImage(imageGameOverText,240,50,null);
                break;
        }

    }
    private void resetGame(){
        mainCharacter.setAlive(true);
        mainCharacter.setX(50);
        enemiesManager.reset();
    }
    public void keyTyped(KeyEvent e){ }
    public void keyPressed(KeyEvent e){
        //mainCharacter.jump();
    }
    public void keyReleased(KeyEvent e){
        //System.out.println("Key Released");
        switch (e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                if(gameState == GAME_FIRST_STATE){
                    gameState = GAME_PLAY_STATE;
                }else if(gameState==GAME_PLAY_STATE){
                    mainCharacter.jump();
                }else if(gameState == GAME_OVER_STATE){
                    resetGame();
                    gameState = GAME_PLAY_STATE;

                }
                break;
        }
    }
}
