import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.swing.JFrame;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    private GameManager gameManager;
    private JFrame testFrame;
    private final File scoreFile = new File("highscore.properties");

    @BeforeEach
    void setUp() {
        if (scoreFile.exists()) {
            scoreFile.delete();
        }
        testFrame = new JFrame("Test Frame");
        gameManager = new GameManager(testFrame);
    }

    @AfterEach
    void tearDown() {
        if (scoreFile.exists()) {
            scoreFile.delete();
        }
        testFrame.dispose();
    }

    @Test
    void testInitialHighScoreIsZero() {
        assertEquals(0, gameManager.getHighScore());
    }

    @Test
    void testHighScoreUpdates() {
        gameManager.gameOver(10);
        assertEquals(10, gameManager.getHighScore());

        gameManager.gameOver(5);
        assertEquals(10, gameManager.getHighScore());

        gameManager.gameOver(20);
        assertEquals(20, gameManager.getHighScore());
    }

    @Test
    void testHighScorePersistence() {
        gameManager.gameOver(50);

        GameManager newGameManager = new GameManager(new JFrame());

        assertEquals(50, newGameManager.getHighScore());
    }
}