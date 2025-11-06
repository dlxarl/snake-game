import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SnakeGame extends JPanel implements ActionListener {
    private static final int TILE_SIZE = 25;
    private static final int BOARD_WIDTH = 20;
    private static final int BOARD_HEIGHT = 20;

    private GameManager gameManager;
    private Difficulty difficulty;
    private ArrayList<Point> snake;
    private Point food;
    private String direction;
    private boolean running;
    private Timer timer;
    private Random random;
    private int score;
    private Clip musicClip;

    public SnakeGame(GameManager gameManager, Difficulty difficulty) {
        this(gameManager, difficulty, new Random());
    }

    SnakeGame(GameManager gameManager, Difficulty difficulty, Random random) {
        this.gameManager = gameManager;
        this.difficulty = difficulty;
        this.random = random;

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

    int getScore() { return score; }
    boolean isRunning() { return running; }
    String getDirection() { return direction; }
    ArrayList<Point> getSnake() { return new ArrayList<>(snake); }
    Point getFood() { return new Point(food); }
    static int getBoardWidth() { return BOARD_WIDTH; }
    static int getBoardHeight() { return BOARD_HEIGHT; }
    void setDirection(String direction) { this.direction = direction; }
    void setFood(Point food) { this.food = food; }

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

        playRandomSongFromFolder("D:\\snake-game-main\\snake-game-main\\src\\main\\java\\music"); // 🎵 случайная песня из папки
    }

    private void playRandomSongFromFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".wav"));

        if (files == null || files.length == 0) {
            System.out.println("Нет доступных песен в папке: " + folderPath);
            return;
        }

        File selected = files[random.nextInt(files.length)];

        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(selected);
            musicClip = AudioSystem.getClip();
            musicClip.open(audioStream);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            System.out.println("Играет: " + selected.getName());
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void spawnFood() {
        int x, y;
        do {
            x = random.nextInt(BOARD_WIDTH);
            y = random.nextInt(BOARD_HEIGHT);
        } while (snake.contains(new Point(x, y)));
        food = new Point(x, y);
    }

    private void handleKeyPress(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                if (!direction.equals("DOWN")) direction = "UP";
                break;
            case KeyEvent.VK_DOWN:
                if (!direction.equals("UP")) direction = "DOWN";
                break;
            case KeyEvent.VK_LEFT:
                if (!direction.equals("RIGHT")) direction = "LEFT";
                break;
            case KeyEvent.VK_RIGHT:
                if (!direction.equals("LEFT")) direction = "RIGHT";
                break;
            case KeyEvent.VK_SPACE:
                if (!running) startGame();
                break;
            case KeyEvent.VK_ESCAPE:
                if (timer != null) {
                    timer.stop();
                }
                gameManager.showStartMenu();
                break;
        }
    }

    void move() {
        Point head = snake.get(0);
        Point newHead = new Point(head);

        switch (direction) {
            case "UP": newHead.y--; break;
            case "DOWN": newHead.y++; break;
            case "LEFT": newHead.x--; break;
            case "RIGHT": newHead.x++; break;
        }

        if (newHead.x < 0 || newHead.x >= BOARD_WIDTH || newHead.y < 0 || newHead.y >= BOARD_HEIGHT || snake.contains(newHead)) {
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

    private void gameOver() {
        running = false;
        timer.stop();
        if (musicClip != null && musicClip.isRunning()) {
            musicClip.stop();
        }
        gameManager.gameOver(score);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (running) {
            g.setColor(Color.YELLOW);
            g.fillOval(food.x * TILE_SIZE, food.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

            for (int i = 0; i < snake.size(); i++) {
                Point p = snake.get(i);
                int lgbt = i % 7;
                if (lgbt == 0) g.setColor(new Color(255, 0, 0));
                else if (lgbt == 1) g.setColor(new Color(255, 151, 0));
                else if (lgbt == 2) g.setColor(new Color(245, 255, 0));
                else if (lgbt == 3) g.setColor(new Color(25, 255, 0));
                else if (lgbt == 4) g.setColor(new Color(0, 244, 255));
                else if (lgbt == 5) g.setColor(new Color(0, 42, 255));
                else if (lgbt == 6) g.setColor(new Color(238, 0, 255));

                g.fillRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(p.x * TILE_SIZE, p.y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Score: " + score, 10, 20);
            g.drawString("Difficulty: " + difficulty.getName(), 10, 40);
            g.drawString("High Score: " + gameManager.getHighScore(), BOARD_WIDTH * TILE_SIZE - 150, 20);
        } else {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 36));
            String msg = "Game Over!";
            FontMetrics metrics = g.getFontMetrics();
            g.drawString(msg, (getWidth() - metrics.stringWidth(msg)) / 2, getHeight() / 2 - 60);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            String scoreMsg = "Score: " + score;
            metrics = g.getFontMetrics();
            g.drawString(scoreMsg, (getWidth() - metrics.stringWidth(scoreMsg)) / 2, getHeight() / 2 - 20);

            if (score > gameManager.getHighScore()) {
                g.setColor(Color.YELLOW);
                String newHighScore = "NEW HIGH SCORE!";
                metrics = g.getFontMetrics();
                g.drawString(newHighScore, (getWidth() - metrics.stringWidth(newHighScore)) / 2, getHeight() / 2 + 20);
            }

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 16));
            String restartMsg = "Press SPACE to restart | ESC for menu";
            metrics = g.getFontMetrics();
            g.drawString(restartMsg, (getWidth() - metrics.stringWidth(restartMsg)) / 2, getHeight() / 2 + 60);
        }
    }
}
