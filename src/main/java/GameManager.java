import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;

public class GameManager {
    private JFrame frame;
    private StartMenu startMenu;
    private SnakeGame snakeGame;
    private int highScore;
    private static final String HIGH_SCORE_FILE = "highscore.properties";
    
    public GameManager(JFrame frame) {
        this.frame = frame;
        loadHighScore();
        showStartMenu();
    }
    
    private void loadHighScore() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(HIGH_SCORE_FILE)) {
            props.load(fis);
            highScore = Integer.parseInt(props.getProperty("highscore", "0"));
        } catch (IOException e) {
            highScore = 0;
        }
    }
    
    private void saveHighScore(int score) {
        if (score > highScore) {
            highScore = score;
            Properties props = new Properties();
            props.setProperty("highscore", String.valueOf(highScore));
            try (FileOutputStream fos = new FileOutputStream(HIGH_SCORE_FILE)) {
                props.store(fos, "Snake Game High Score");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void showStartMenu() {
        if (snakeGame != null) {
            frame.remove(snakeGame);
        }
        startMenu = new StartMenu(this, highScore);
        frame.add(startMenu);
        frame.pack();
        frame.revalidate();
        frame.repaint();
        startMenu.requestFocusInWindow();
    }
    
    public void startGame(Difficulty difficulty) {
        if (startMenu != null) {
            frame.remove(startMenu);
        }
        snakeGame = new SnakeGame(this, difficulty);
        frame.add(snakeGame);
        frame.pack();
        frame.revalidate();
        frame.repaint();
        snakeGame.requestFocusInWindow();
    }
    
    public void gameOver(int score) {
        saveHighScore(score);
    }
    
    public int getHighScore() {
        return highScore;
    }
}
