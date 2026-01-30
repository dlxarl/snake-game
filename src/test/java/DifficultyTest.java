import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DifficultyTest {

    @Test
    void testDifficultyValues() {
        assertEquals(150, Difficulty.EASY.getDelay());
        assertEquals("Easy", Difficulty.EASY.getName());

        assertEquals(100, Difficulty.NORMAL.getDelay());
        assertEquals("Normal", Difficulty.NORMAL.getName());

        assertEquals(70, Difficulty.HARD.getDelay());
        assertEquals("Hard", Difficulty.HARD.getName());

        assertEquals(40, Difficulty.EXTREME.getDelay());
        assertEquals("Extreme", Difficulty.EXTREME.getName());
    }
}