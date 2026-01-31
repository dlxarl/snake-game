import org.junit.jupiter.api.Test;
import javax.swing.JFrame;

import java.awt.event.KeyEvent;
import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;

class StartMenuTest {

    private TestGameManager gameManager;

    static class TestGameManager extends GameManager {
        boolean showMenuCalled = false;

        TestGameManager() {
            super((javax.swing.JFrame) null);
        }

        @Override
        public void showStartMenu() {
            showMenuCalled = true; // без frame
        }
    }

    @Test
    void testStartMenuCreation() {
        JFrame frame = new JFrame("Test");
        GameManager manager = new GameManager(frame);
        
        assertDoesNotThrow(() -> {
            StartMenu menu = new StartMenu(manager, 0);
            assertNotNull(menu);
        });
    }
    
    @Test
    void testStartMenuWithHighScore() {
        JFrame frame = new JFrame("Test");
        GameManager manager = new GameManager(frame);
        
        StartMenu menu = new StartMenu(manager, 100);
        assertNotNull(menu);
    }
    
    @Test
    void testStartMenuDimensions() {
        JFrame frame = new JFrame("Test");
        GameManager manager = new GameManager(frame);
        StartMenu menu = new StartMenu(manager, 0);
        
        assertEquals(500, menu.getPreferredSize().width);
        assertEquals(600, menu.getPreferredSize().height);
    }

    @Test
    void testHandleKeyPressUpAndW() {
        SecureRandom random = new SecureRandom();
        SnakeGame game = new SnakeGame(gameManager, Difficulty.NORMAL, random);

        game.setDirection("RIGHT");
        game.handleKeyPress(KeyEvent.VK_UP);
        assertEquals("UP", game.getDirection());

        game.setDirection("RIGHT");
        game.handleKeyPress(KeyEvent.VK_W);
        assertEquals("UP", game.getDirection());
    }

    @Test
    void testHandleKeyPressDownAndS() {
        SecureRandom random = new SecureRandom();
        SnakeGame game = new SnakeGame(gameManager, Difficulty.NORMAL, random);

        game.setDirection("RIGHT");
        game.handleKeyPress(KeyEvent.VK_DOWN);
        assertEquals("DOWN", game.getDirection());

        game.setDirection("RIGHT");
        game.handleKeyPress(KeyEvent.VK_S);
        assertEquals("DOWN", game.getDirection());
    }

    @Test
    void testHandleKeyPressLeftAndA() {
        SecureRandom random = new SecureRandom();
        SnakeGame game = new SnakeGame(gameManager, Difficulty.NORMAL, random);

        game.setDirection("UP");
        game.handleKeyPress(KeyEvent.VK_LEFT);
        assertEquals("LEFT", game.getDirection());

        game.setDirection("UP");
        game.handleKeyPress(KeyEvent.VK_A);
        assertEquals("LEFT", game.getDirection());
    }

    @Test
    void testHandleKeyPressRightAndD() {
        SecureRandom random = new SecureRandom();
        SnakeGame game = new SnakeGame(gameManager, Difficulty.NORMAL, random);

        game.setDirection("UP");
        game.handleKeyPress(KeyEvent.VK_RIGHT);
        assertEquals("RIGHT", game.getDirection());

        game.setDirection("UP");
        game.handleKeyPress(KeyEvent.VK_D);
        assertEquals("RIGHT", game.getDirection());
    }

    @Test
    void testHandleKeyPressDoesNotReverseDirection() {
        SecureRandom random = new SecureRandom();
        SnakeGame game = new SnakeGame(gameManager, Difficulty.NORMAL, random);

        game.setDirection("UP");
        game.handleKeyPress(KeyEvent.VK_DOWN);
        assertEquals("UP", game.getDirection());

        game.setDirection("DOWN");
        game.handleKeyPress(KeyEvent.VK_UP);
        assertEquals("DOWN", game.getDirection());

        game.setDirection("LEFT");
        game.handleKeyPress(KeyEvent.VK_RIGHT);
        assertEquals("LEFT", game.getDirection());

        game.setDirection("RIGHT");
        game.handleKeyPress(KeyEvent.VK_LEFT);
        assertEquals("RIGHT", game.getDirection());
    }

//    @Test
//    void testHandleKeyPressSpaceRestartsWhenNotRunning() {
//        SecureRandom random = new SecureRandom();
//        SnakeGame game = new SnakeGame(gameManager, Difficulty.NORMAL,random);
//
//        game.setDirection("LEFT");
//        for (int i = 0; i < SnakeGame.getBoardWidth(); i++) {
//            game.move();
//        }
//        assertFalse(game.isRunning());
//
//        game.handleKeyPress(KeyEvent.VK_SPACE);
//
//        assertTrue(game.isRunning());
//        assertEquals(0, game.getScore());
//        assertEquals(1, game.getSnake().size());
//        assertEquals("RIGHT", game.getDirection());
//    }

//    @Test
//    void testHandleKeyPressEscapeCallsShowStartMenu() {
//        SecureRandom random = new SecureRandom();
//        SnakeGame game = new SnakeGame(gameManager, Difficulty.NORMAL,random);
//
//        assertFalse(gameManager.showMenuCalled);
//        game.handleKeyPress(KeyEvent.VK_ESCAPE);
//        assertTrue(gameManager.showMenuCalled);
//    }
}
