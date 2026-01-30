import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Główny komponent gry Snake.
 * <p>
 * Ta klasa implementuje całą logikę gry:
 * <ul>
 * <li>Ruch węża po planszy</li>
 * <li>Obsługa kolizji ze ścianami i własnym ciałem</li>
 * <li>Zbieranie jedzenia i zwiększanie wyniku</li>
 * <li>Wyświetlanie stanu gry na ekranie</li>
 * <li>Obsługa wprowadzania z klawiatury</li>
 * </ul>
 * </p>
 *
 * @author Team 6 (vosadcha, abondarchuk)
 * @version 1.0
 * @see GameManager
 * @see Difficulty
 */
public class SnakeGame extends JPanel implements ActionListener {

    /** Rozmiar jednej komórki w pikselach */
    private static final int TILE_SIZE = 25;

    /** Szerokość planszy w komórkach */
    private static final int BOARD_WIDTH = 20;

    /** Wysokość planszy w komórkach */
    private static final int BOARD_HEIGHT = 20;

    /** Referencja do menedżera gry */
    private GameManager gameManager;

    /** Aktualny poziom trudności */
    private Difficulty difficulty;

    /** Lista segmentów ciała węża (głowa to pierwszy element) */
    private ArrayList<Point> snake;

    /** Pozycja jedzenia na planszy */
    private Point food;

    /** Aktualny kierunek ruchu węża ("UP", "DOWN", "LEFT", "RIGHT") */
    private String direction;

    /** Flaga wskazująca czy gra jest aktywna */
    private boolean running;

    /** Timer do aktualizacji gry */
    private Timer timer;

    /** Generator liczb losowych do umieszczania jedzenia */
    private Random random;

    /** Aktualny wynik gracza */
    private int score;

    /** Klip do odtwarzania muzyki */
    private Clip musicClip;

    /** Niestandardowa czcionka do wyświetlania tekstu */
    private Font customFont;

    /**
     * Tworzy nową grę Snake ze standardowym generatorem liczb losowych.
     *
     * @param gameManager menedżer gry do komunikacji zwrotnej
     * @param difficulty  poziom trudności gry
     */
    public SnakeGame(GameManager gameManager, Difficulty difficulty) {
        this(gameManager, difficulty, new Random());
    }

    /**
     * Tworzy nową grę Snake z określonym generatorem liczb losowych.
     * <p>
     * Ten konstruktor jest przeznaczony do testowania, umożliwiając
     * kontrolę generowania liczb losowych.
     * </p>
     *
     * @param gameManager menedżer gry do komunikacji zwrotnej
     * @param difficulty  poziom trudności gry
     * @param random      generator liczb losowych
     */
    SnakeGame(GameManager gameManager, Difficulty difficulty, Random random) {
        this.gameManager = gameManager;
        this.difficulty = difficulty;
        this.random = random;

        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/assets/fonts/ElmsSans-SemiBold.ttf"))
                    .deriveFont(Font.PLAIN, 16f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
            customFont = new Font("SansSerif", Font.PLAIN, 16);
        }

        setPreferredSize(new Dimension(BOARD_WIDTH * TILE_SIZE, BOARD_HEIGHT * TILE_SIZE));
        setBackground(Color.PINK);
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e.getKeyCode());
            }
        });

        startGame();
    }

    /**
     * Zwraca aktualny wynik gracza.
     *
     * @return liczba zebranego jedzenia
     */
    int getScore() {
        return score;
    }

    /**
     * Sprawdza czy gra jest jeszcze aktywna.
     *
     * @return {@code true} jeśli gra trwa; {@code false} jeśli gra się skończyła
     */
    boolean isRunning() {
        return running;
    }

    /**
     * Zwraca aktualny kierunek ruchu węża.
     *
     * @return ciąg kierunku: "UP", "DOWN", "LEFT" lub "RIGHT"
     */
    String getDirection() {
        return direction;
    }

    /**
     * Zwraca kopię listy segmentów węża.
     *
     * @return nowa lista punktów reprezentujących ciało węża
     */
    ArrayList<Point> getSnake() {
        return new ArrayList<>(snake);
    }

    /**
     * Zwraca kopię pozycji jedzenia.
     *
     * @return nowy punkt ze współrzędnymi jedzenia
     */
    Point getFood() {
        return new Point(food);
    }

    /**
     * Zwraca szerokość planszy w komórkach.
     *
     * @return szerokość planszy
     */
    static int getBoardWidth() {
        return BOARD_WIDTH;
    }

    /**
     * Zwraca wysokość planszy w komórkach.
     *
     * @return wysokość planszy
     */
    static int getBoardHeight() {
        return BOARD_HEIGHT;
    }

    /**
     * Ustawia kierunek ruchu węża.
     *
     * @param direction nowy kierunek ("UP", "DOWN", "LEFT", "RIGHT")
     */
    void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * Ustawia pozycję jedzenia.
     *
     * @param food nowa pozycja jedzenia
     */
    void setFood(Point food) {
        this.food = food;
    }

    /**
     * Inicjalizuje i uruchamia nową grę.
     * <p>
     * Wykonuje następujące czynności:
     * <ol>
     * <li>Tworzy węża z jednym segmentem w centrum planszy</li>
     * <li>Ustawia początkowy kierunek w prawo</li>
     * <li>Resetuje wynik do zera</li>
     * <li>Umieszcza jedzenie na losowej pozycji</li>
     * <li>Uruchamia timer gry</li>
     * </ol>
     * </p>
     */
    private void startGame() {
        snake = new ArrayList<>();
        snake.add(new Point(BOARD_WIDTH / 2, BOARD_HEIGHT / 2));
        direction = "RIGHT";
        score = 0;
        spawnFood();
        running = true;

        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(difficulty.getDelay(), this);
        timer.start();
    }

    /**
     * Umieszcza jedzenie na losowej wolnej pozycji.
     * <p>
     * Generuje losowe współrzędne dopóki nie znajdzie
     * pozycji, która nie przecina się z ciałem węża.
     * </p>
     */
    private void spawnFood() {
        int x, y;
        do {
            x = random.nextInt(BOARD_WIDTH);
            y = random.nextInt(BOARD_HEIGHT);
        } while (snake.contains(new Point(x, y)));
        food = new Point(x, y);
    }

    /**
     * Obsługuje naciśnięcia klawiszy.
     * <p>
     * Obsługiwane klawisze:
     * <ul>
     * <li>Strzałki lub W/A/S/D - zmiana kierunku ruchu</li>
     * <li>Spacja - restart gry po Game Over</li>
     * <li>Escape - powrót do menu głównego</li>
     * </ul>
     * </p>
     *
     * @param keyCode kod naciśniętego klawisza
     */
    private void handleKeyPress(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if (!direction.equals("DOWN"))
                    direction = "UP";
                break;

            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if (!direction.equals("UP"))
                    direction = "DOWN";
                break;

            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                if (!direction.equals("RIGHT"))
                    direction = "LEFT";
                break;

            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if (!direction.equals("LEFT"))
                    direction = "RIGHT";
                break;

            case KeyEvent.VK_SPACE:
                if (!running)
                    startGame();
                break;

            case KeyEvent.VK_ESCAPE:
                if (timer != null) {
                    timer.stop();
                }
                gameManager.showStartMenu();
                break;
        }
    }

    /**
     * Wykonuje jeden krok ruchu węża.
     * <p>
     * Oblicza nową pozycję głowy na podstawie aktualnego kierunku,
     * sprawdza kolizje ze ścianami i ciałem, obsługuje
     * zbieranie jedzenia i aktualizuje pozycję węża.
     * </p>
     */
    void move() {
        Point head = snake.get(0);
        Point newHead = new Point(head);

        switch (direction) {
            case "UP":
                newHead.y--;
                break;
            case "DOWN":
                newHead.y++;
                break;
            case "LEFT":
                newHead.x--;
                break;
            case "RIGHT":
                newHead.x++;
                break;
        }

        if (newHead.x < 0 || newHead.x >= BOARD_WIDTH || newHead.y < 0 || newHead.y >= BOARD_HEIGHT
                || snake.contains(newHead)) {
            gameOver();
            return;
        }

        snake.add(0, newHead);

        if (newHead.equals(food)) {
            score++;
            spawnFood();
        } else {
            snake.remove(snake.size() - 1);
        }
    }

    /**
     * Obsługuje zakończenie gry.
     * <p>
     * Zatrzymuje timer, muzykę i informuje menedżera gry
     * o końcowym wyniku.
     * </p>
     */
    private void gameOver() {
        running = false;
        timer.stop();
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
        }
        gameManager.gameOver(score);
    }

    /**
     * Obsługa zdarzenia timera.
     * <p>
     * Wywoływana okresowo do aktualizacji stanu gry.
     * Jeśli gra jest aktywna, wykonuje krok ruchu i odświeża ekran.
     * </p>
     *
     * @param e zdarzenie timera
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            repaint();
        }
    }

    /**
     * Rysuje obiekty gry na ekranie.
     * <p>
     * Podczas gry wyświetla:
     * <ul>
     * <li>Jedzenie (żółte koło)</li>
     * <li>Węża (segmenty w kolorach tęczy)</li>
     * <li>Wynik, poziom trudności i rekord</li>
     * </ul>
     * Po zakończeniu gry pokazuje ekran Game Over z opcjami.
     * </p>
     *
     * @param g kontekst graficzny do rysowania
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (running) {
            // Rysowanie jedzenia
            g.setColor(Color.YELLOW);
            g.fillOval(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            g.setColor(Color.BLACK);
            g.drawOval(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

            // Rysowanie węża z efektem tęczy
            for (int i = 0; i < snake.size(); i++) {
                Point p = snake.get(i);
                int rainbow = i % 7;
                if (rainbow == 0)
                    g.setColor(new Color(255, 0, 0));
                else if (rainbow == 1)
                    g.setColor(new Color(255, 151, 0));
                else if (rainbow == 2)
                    g.setColor(new Color(245, 255, 0));
                else if (rainbow == 3)
                    g.setColor(new Color(25, 255, 0));
                else if (rainbow == 4)
                    g.setColor(new Color(0, 244, 255));
                else if (rainbow == 5)
                    g.setColor(new Color(0, 42, 255));
                else if (rainbow == 6)
                    g.setColor(new Color(238, 0, 255));

                g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }

            // Wyświetlanie informacji
            g.setColor(Color.WHITE);
            g.setFont(customFont.deriveFont(16f));
            g.drawString("Score: " + score, 10, 20);
            g.drawString("Difficulty: " + difficulty.getName(), 10, 40);
            g.drawString("High Score: " + gameManager.getHighScore(), BOARD_WIDTH * TILE_SIZE - 150, 20);
        } else {
            // Ekran Game Over
            g.setColor(Color.WHITE);
            g.setFont(customFont.deriveFont(36f));
            String msg = "Game Over!";
            FontMetrics metrics = g.getFontMetrics();
            g.drawString(msg, (getWidth() - metrics.stringWidth(msg)) / 2, getHeight() / 2 - 60);

            g.setFont(customFont.deriveFont(20f));
            String scoreMsg = "Score: " + score;
            metrics = g.getFontMetrics();
            g.drawString(scoreMsg, (getWidth() - metrics.stringWidth(scoreMsg)) / 2, getHeight() / 2 - 20);

            // Komunikat o nowym rekordzie
            if (score > gameManager.getHighScore()) {
                g.setColor(Color.YELLOW);
                String newHighScore = "NEW HIGH SCORE!";
                metrics = g.getFontMetrics();
                g.drawString(newHighScore, (getWidth() - metrics.stringWidth(newHighScore)) / 2, getHeight() / 2 + 20);
            }

            // Instrukcje dla gracza
            g.setColor(Color.WHITE);
            g.setFont(customFont.deriveFont(16f));
            String restartMsg = "Press SPACE to restart | ESC for menu";
            metrics = g.getFontMetrics();
            g.drawString(restartMsg, (getWidth() - metrics.stringWidth(restartMsg)) / 2, getHeight() / 2 + 60);
        }
    }
}
