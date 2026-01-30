import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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

        game.setDirection("RIGHT");
        game.move();
        assertEquals(start.x + 1, game.getSnake().get(0).x);

        game.setDirection("DOWN");
        game.move();
        assertEquals(start.y + 1, game.getSnake().get(0).y);

        game.setDirection("LEFT");
        game.move();
        assertEquals(start.x, game.getSnake().get(0).x);
    }

    @Test
    void testFoodEatingIncreasesScore() {
        SnakeGame game = new SnakeGame(gameManager, Difficulty.NORMAL, new Random(42));
        int initialScore = game.getScore();

        Point head = game.getSnake().get(0);
        game.setFood(new Point(head.x + 1, head.y));

        game.move();

        assertEquals(initialScore + 1, game.getScore());
    }

    @Test
    void testFoodEatingGrowsSnake() {
        SnakeGame game = new SnakeGame(gameManager, Difficulty.NORMAL, new Random(42));
        int initialLength = game.getSnake().size();

        Point head = game.getSnake().get(0);
        game.setFood(new Point(head.x + 1, head.y));

        game.move();

        assertEquals(initialLength + 1, game.getSnake().size());
    }

    @Test
    void testWallCollisionStopsGame() {
        SnakeGame game = new SnakeGame(gameManager, Difficulty.NORMAL);

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

    @Test
    void testSelfCollisionEndsGame() {
        SnakeGame game = new SnakeGame(gameManager, Difficulty.NORMAL, new Random(42));

        Point head;
        for (int i = 0; i < 4; i++) {
            head = game.getSnake().get(0);
            game.setFood(new Point(head.x + 1, head.y));
            game.move();
        }

        assertEquals(5, game.getSnake().size());

        game.setDirection("DOWN");
        game.move();

        game.setDirection("LEFT");
        game.move();

        game.setDirection("UP");
        game.move();

        game.setDirection("RIGHT");
        game.move();

        assertFalse(game.isRunning());
    }
}