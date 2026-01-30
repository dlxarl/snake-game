import javax.swing.*;
import java.awt.*;

/**
 * Komponent menu startowego gry Snake.
 * <p>
 * Zapewnia interfejs użytkownika do:
 * <ul>
 * <li>Wyświetlania tytułu gry i rekordu</li>
 * <li>Wyboru poziomu trudności</li>
 * <li>Uruchamiania nowej gry</li>
 * <li>Wyświetlania instrukcji sterowania</li>
 * </ul>
 * </p>
 *
 * @author Team 6 (vosadcha, abondarchuk)
 * @version 1.0
 * @see GameManager
 * @see Difficulty
 */
public class StartMenu extends JPanel {

    /** Menedżer gry do przejścia do ekranu gry */
    private GameManager gameManager;

    /** Aktualny rekord do wyświetlania */
    private int highScore;

    /** Wybrany poziom trudności */
    private Difficulty selectedDifficulty;

    /** Tablica przycisków wyboru trudności */
    private JButton[] difficultyButtons;

    /**
     * Tworzy nowe menu startowe.
     *
     * @param gameManager menedżer gry do komunikacji zwrotnej
     * @param highScore   aktualny rekord do wyświetlania
     */
    public StartMenu(GameManager gameManager, int highScore) {
        this.gameManager = gameManager;
        this.highScore = highScore;
        this.selectedDifficulty = Difficulty.NORMAL;

        setPreferredSize(new Dimension(500, 600));
        setBackground(Color.PINK);
        setLayout(null);

        createComponents();
    }

    /**
     * Tworzy i rozmieszcza wszystkie komponenty interfejsu.
     * <p>
     * Zawiera:
     * <ul>
     * <li>Nagłówek "SNAKE"</li>
     * <li>Wyświetlanie rekordu</li>
     * <li>Przyciski wyboru trudności (Easy, Normal, Hard, Extreme)</li>
     * <li>Przycisk uruchomienia gry</li>
     * <li>Instrukcje sterowania</li>
     * </ul>
     * </p>
     */
    private void createComponents() {
        // Ładowanie niestandardowej czcionki
        Font customFont = null;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/assets/fonts/ElmsSans-SemiBold.ttf"))
                    .deriveFont(Font.PLAIN, 24f);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
            // Czcionka rezerwowa
            customFont = new Font("SansSerif", Font.PLAIN, 24);
        }

        // Tytuł gry
        JLabel titleLabel = new JLabel("SNAKE", SwingConstants.CENTER);
        titleLabel.setFont(customFont.deriveFont(48f));
        titleLabel.setForeground(new Color(16, 88, 9));
        titleLabel.setBounds(0, 30, 500, 60);
        add(titleLabel);

        // Wyświetlanie rekordu
        JLabel highScoreLabel = new JLabel("High Score: " + highScore, SwingConstants.CENTER);
        highScoreLabel.setFont(customFont.deriveFont(18f));
        highScoreLabel.setForeground(Color.YELLOW);
        highScoreLabel.setBounds(0, 80, 500, 30);
        add(highScoreLabel);

        // Etykieta wyboru trudności
        JLabel difficultyLabel = new JLabel("Select Difficulty:", SwingConstants.CENTER);
        difficultyLabel.setFont(customFont.deriveFont(20f));
        difficultyLabel.setForeground(new Color(16, 88, 9));
        difficultyLabel.setBounds(0, 150, 500, 30);
        add(difficultyLabel);

        // Tworzenie przycisków wyboru trudności
        Difficulty[] difficulties = Difficulty.values();
        difficultyButtons = new JButton[difficulties.length];

        for (int i = 0; i < difficulties.length; i++) {
            Difficulty diff = difficulties[i];
            JButton button = new JButton(diff.getName());
            button.setBounds(150, 200 + (i * 60), 200, 45);
            button.setFont(customFont.deriveFont(18f));

            button.setFocusPainted(false);
            button.setRolloverEnabled(false);
            button.setContentAreaFilled(false);
            button.setOpaque(true);
            button.setBorderPainted(true);

            button.addActionListener(e -> selectDifficulty(diff));

            difficultyButtons[i] = button;
            add(button);
        }

        // Przycisk uruchomienia gry
        JButton startButton = new JButton("START GAME");
        startButton.setBounds(125, 500, 250, 60);
        startButton.setFont(customFont.deriveFont(24f));
        startButton.setBackground(new Color(16, 88, 9));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setOpaque(true);
        startButton.setBorderPainted(false);
        startButton.addActionListener(e -> gameManager.startGame(selectedDifficulty));
        add(startButton);

        // Instrukcje sterowania
        JLabel instructionsLabel = new JLabel(
                "<html><center>Use Arrow Keys to move<br>Press ESC to return to menu</center></html>",
                SwingConstants.CENTER);
        instructionsLabel.setFont(customFont.deriveFont(14f));
        instructionsLabel.setForeground(Color.WHITE);
        instructionsLabel.setBounds(0, 450, 500, 40);
        add(instructionsLabel);

        updateDifficultyButtons();
    }

    /**
     * Obsługuje wybór poziomu trudności.
     *
     * @param difficulty wybrany poziom trudności
     */
    private void selectDifficulty(Difficulty difficulty) {
        selectedDifficulty = difficulty;
        updateDifficultyButtons();
    }

    /**
     * Aktualizuje wizualny stan przycisków trudności.
     * <p>
     * Wyróżnia wybrany przycisk zielonym kolorem z żółtą ramką,
     * pozostałe przyciski są wyświetlane w kolorze żółtym.
     * </p>
     */
    private void updateDifficultyButtons() {
        Difficulty[] difficulties = Difficulty.values();
        for (int i = 0; i < difficulties.length; i++) {
            JButton button = difficultyButtons[i];
            boolean isSelected = difficulties[i] == selectedDifficulty;

            if (isSelected) {
                button.setBackground(new Color(16, 88, 9));
                button.setForeground(Color.WHITE);
                button.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
            } else {
                button.setBackground(Color.YELLOW);
                button.setForeground(new Color(16, 88, 9));
                button.setBorder(BorderFactory.createLineBorder(new Color(16, 88, 9), 2));
            }
        }

        revalidate();
        repaint();
    }
}