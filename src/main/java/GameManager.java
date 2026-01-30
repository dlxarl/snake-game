import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;

/**
 * Menedżer gry zarządzający przejściami między ekranami i stanem gry.
 * <p>
 * Ta klasa odpowiada za:
 * <ul>
 * <li>Zarządzanie przejściami między menu startowym a grą</li>
 * <li>Ładowanie i zapisywanie najlepszego wyniku</li>
 * <li>Zarządzanie muzyką w tle</li>
 * <li>Obsługę zakończenia gry</li>
 * </ul>
 * </p>
 *
 * @author Team 6 (vosadcha, abondarchuk)
 * @version 1.0
 * @see SnakeGame
 * @see StartMenu
 * @see MusicPlayer
 */
public class GameManager {

    /** Główne okno programu */
    private JFrame frame;

    /** Menu startowe gry */
    private StartMenu startMenu;

    /** Komponent gry Snake */
    private SnakeGame snakeGame;

    /** Aktualny rekord gracza */
    private int highScore;

    /** Nazwa pliku do zapisywania rekordu */
    private static final String HIGH_SCORE_FILE = "highscore.properties";

    /** Odtwarzacz muzyki w tle */
    private final MusicPlayer musicPlayer;

    /**
     * Tworzy nowy menedżer gry.
     * <p>
     * Inicjalizuje menedżer, ładuje zapisany rekord,
     * wyświetla menu startowe i uruchamia muzykę w tle.
     * </p>
     *
     * @param frame główne okno JFrame, w którym będzie wyświetlana gra
     */
    public GameManager(JFrame frame) {
        this.frame = frame;
        loadHighScore();
        showStartMenu();

        musicPlayer = new MusicPlayer();
        musicPlayer.playRandomSong("assets/music");
    }

    /**
     * Ładuje rekord z pliku.
     * <p>
     * Odczytuje wartość rekordu z pliku properties.
     * Jeśli plik nie istnieje lub zawiera błędy, ustawia rekord na 0.
     * </p>
     */
    private void loadHighScore() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(HIGH_SCORE_FILE)) {
            props.load(fis);
            highScore = Integer.parseInt(props.getProperty("highscore", "0"));
        } catch (IOException e) {
            highScore = 0;
        }
    }

    /**
     * Zapisuje nowy rekord do pliku.
     * <p>
     * Jeśli przekazany wynik przekracza aktualny rekord,
     * aktualizuje rekord i zapisuje go do pliku.
     * </p>
     *
     * @param score wynik do porównania z rekordem
     */
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

    /**
     * Wyświetla menu startowe.
     * <p>
     * Usuwa aktualną grę (jeśli istnieje) i pokazuje menu z możliwością
     * wyboru trudności oraz przeglądania rekordu.
     * </p>
     */
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

    /**
     * Uruchamia nową grę z wybraną trudnością.
     * <p>
     * Usuwa menu startowe i tworzy nowy komponent gry
     * z określonym poziomem trudności.
     * </p>
     *
     * @param difficulty poziom trudności dla nowej gry
     * @see Difficulty
     */
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

    /**
     * Zwraca odtwarzacz muzyki.
     *
     * @return obiekt {@link MusicPlayer} do zarządzania muzyką
     */
    public MusicPlayer getMusicPlayer() {
        return musicPlayer;
    }

    /**
     * Obsługuje zakończenie gry.
     * <p>
     * Zapisuje wynik jako nowy rekord, jeśli przekracza poprzedni.
     * </p>
     *
     * @param score końcowy wynik gracza
     */
    public void gameOver(int score) {
        saveHighScore(score);
    }

    /**
     * Zwraca aktualny rekord.
     *
     * @return najwyższy osiągnięty wynik
     */
    public int getHighScore() {
        return highScore;
    }
}
