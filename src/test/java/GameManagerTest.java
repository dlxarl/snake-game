import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;
import javax.swing.JFrame;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class SnakeGameTest {
    
    private GameManager gameManager;
    private JFrame testFrame;
    
    @BeforeEach
    void setUp() {
        testFrame = new JFrame("Test");
        gameManager = new GameManager(testFrame);
    }
    
    @Test
    void testSnakeGameInitialization() {
        SnakeGame game = new SnakeGame(gameManager, Difficulty.NORMAL);
        
        assertNotNull(game);
        assertTrue(game.isRunning());
        assertEquals(0, game.getScore());
        assertEquals("RIGHT", game.getDirection());
    }
    
    @Test
    void testSnakeStartsAtCenter() {
        SnakeGame game = new SnakeGame(gameManager, Difficulty.NORMAL);
        ArrayList<Point> snake = game.getSnake();
        
        assertEquals(1, snake.size());
        Point head = snake.get(0);
        assertEquals(SnakeGame.getBoardWidth() / 2, head.x);
        assertEquals(SnakeGame.getBoardHeight() / 2, head.y);
    }
    
    @Test
    void testSnakeMovement() {
        SnakeGame game = new SnakeGame(gameManager, Difficulty.NORMAL);
        Point initialHead = game.getSnake().get(0);
        
        game.move();
        
        Point newHead = game.getSnake().get(0);
        assertEquals(initialHead.x + 1, newHead.x);
        assertEquals(initialHead.y, newHead.y);
    }
    
    @Test
    void testSnakeMovementDirections() {
        SnakeGame game = new SnakeGame(gameManager, Difficulty.NORMAL);
        Point start = game.getSnake().get(0);
        
        // Move right
        game.setDirection("RIGHT");
        game.move();
        assertEquals(start.x + 1, game.getSnake().get(0).x);
        
        // Move down
        game.setDirection("DOWN");
        game.move();
        assertEquals(start.y + 2, game.getSnake().get(0).y);
        
        // Move left
        game.setDirection("LEFT");
        game.move();
        assertEquals(start.x + 1, game.getSnake().get(0).x);
    }
    
    @Test
    void testFoodEatingIncreasesScore() {
        SnakeGame game = new SnakeGame(gameManager, Difficulty.NORMAL, new Random(42));
        int initialScore = game.getScore();
        
        // Position food right in front of snake
        Point head = game.getSnake().get(0);
        game.setFood(new Point(head.x + 1, head.y));
        
        game.move();
        
        assertEquals(initialScore + 1, game.getScore());
    }
    
    @Test
    void testFoodEatingGrowsSnake() {
        SnakeGame game = new SnakeGame(gameManager, Difficulty.NORMAL, new Random(42));
        int initialLength = game.getSnake().size();
        
        // Position food right in front of snake
        Point head = game.getSnake().get(0);
        game.setFood(new Point(head.x + 1, head.y));
        
        game.move();
        
        assertEquals(initialLength + 1, game.getSnake().size());
    }
    
    @Test
    void testWallCollisionStopsGame() {
        SnakeGame game = new SnakeGame(gameManager, Difficulty.NORMAL);
        
        // Move snake to the edge
        game.setDirection("LEFT");
        for (int i = 0; i < SnakeGame.getBoardWidth(); i++) {
            game.move();
        }
        
        assertFalse(game.isRunning());
    }
    
    @Test
    void testFoodSpawnLocation() {
        SnakeGame game = new SnakeGame(gameManager, Difficulty.NORMAL);
        Point food = game.getFood();
        
        assertNotNull(food);
        assertTrue(food.x >= 0 && food.x < SnakeGame.getBoardWidth());
        assertTrue(food.y >= 0 && food.y < SnakeGame.getBoardHeight());
    }
    
    @Test
    void testDifferentDifficultiesCreateDifferentGames() {
        SnakeGame easyGame = new SnakeGame(gameManager, Difficulty.EASY);
        SnakeGame hardGame = new SnakeGame(gameManager, Difficulty.HARD);
        
        assertNotNull(easyGame);
        assertNotNull(hardGame);
        assertTrue(easyGame.isRunning());
        assertTrue(hardGame.isRunning());
    }
}
