import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DifficultyTest {
    
    @Test
    void testDifficultyDelays() {
        assertEquals(150, Difficulty.EASY.getDelay());
        assertEquals(100, Difficulty.NORMAL.getDelay());
        assertEquals(70, Difficulty.HARD.getDelay());
        assertEquals(40, Difficulty.EXTREME.getDelay());
    }
    
    @Test
    void testDifficultyNames() {
        assertEquals("Easy", Difficulty.EASY.getName());
        assertEquals("Normal", Difficulty.NORMAL.getName());
        assertEquals("Hard", Difficulty.HARD.getName());
        assertEquals("Extreme", Difficulty.EXTREME.getName());
    }
    
    @Test
    void testAllDifficultiesExist() {
        Difficulty[] difficulties = Difficulty.values();
        assertEquals(4, difficulties.length);
    }
    
    @Test
    void testDifficultyOrdering() {
        Difficulty[] difficulties = Difficulty.values();
        // Check that delays decrease (speed increases)
        assertTrue(difficulties[0].getDelay() > difficulties[1].getDelay());
        assertTrue(difficulties[1].getDelay() > difficulties[2].getDelay());
        assertTrue(difficulties[2].getDelay() > difficulties[3].getDelay());
    }
}
