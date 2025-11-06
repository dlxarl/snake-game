import org.junit.jupiter.api.Test;
import javax.swing.JFrame;

import static org.junit.jupiter.api.Assertions.*;

class StartMenuTest {
    
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
}
