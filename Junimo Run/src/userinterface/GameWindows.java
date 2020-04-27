package userinterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameWindows extends JFrame {
    private GameScreen gameScreen;
    public GameWindows(){
        super("JunimoRun");
        setSize(600,200);
        setLocation(400,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameScreen = new GameScreen();
        add(gameScreen);
        addKeyListener(gameScreen);
    }
    public void startGame(){
        gameScreen.startGame();
    }
    public static void main(String args[]){
        GameWindows gw = new GameWindows();
        gw.setVisible(true);
        gw.startGame();

    }

}
