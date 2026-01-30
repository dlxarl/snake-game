import javax.swing.*;

/**
 * Główna klasa do uruchomienia gry Snake.
 * <p>
 * Ta klasa zawiera punkt wejścia do programu i inicjalizuje interfejs
 * graficzny.
 * Używa Swing do tworzenia okna gry i uruchomienia {@link GameManager}.
 * </p>
 *
 * @author Team 6 (vosadcha, abondarchuk)
 * @version 1.0
 * @see GameManager
 * @see SnakeGame
 */
public class Main {

    /**
     * Punkt wejścia do programu.
     * <p>
     * Wykonuje następujące kroki:
     * <ol>
     * <li>Ustawia wieloplatformowy Look and Feel</li>
     * <li>Tworzy główne okno JFrame</li>
     * <li>Inicjalizuje GameManager do zarządzania grą</li>
     * <li>Konfiguruje i wyświetla okno</li>
     * </ol>
     * </p>
     *
     * @param args argumenty wiersza poleceń (nieużywane)
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Snake");
            GameManager gameManager = new GameManager(frame);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
