import javax.swing.*;
import java.awt.*;

public class StartMenu extends JPanel {
    private GameManager gameManager;
    private int highScore;
    private Difficulty selectedDifficulty;
    private JButton[] difficultyButtons;
    
    public StartMenu(GameManager gameManager, int highScore) {
        this.gameManager = gameManager;
        this.highScore = highScore;
        this.selectedDifficulty = Difficulty.NORMAL;
        
        setPreferredSize(new Dimension(500, 600));
        setBackground(Color.PINK);
        setLayout(null);
        
        createComponents();
    }
    
    private void createComponents() {
        // Title
        JLabel titleLabel = new JLabel("SNAKE GAME", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground (new Color(16, 88, 9));;
        titleLabel.setBounds(0, 50, 500, 60);
        add(titleLabel);
        
        // High Score
        JLabel highScoreLabel = new JLabel("High Score: " + highScore, SwingConstants.CENTER);
        highScoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        highScoreLabel.setForeground(Color.YELLOW);
        highScoreLabel.setBounds(0, 130, 500, 30);
        add(highScoreLabel);
        
        // Difficulty Label
        JLabel difficultyLabel = new JLabel("Select Difficulty:", SwingConstants.CENTER);
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 20));
        difficultyLabel.setForeground(new Color(16, 88, 9));
        difficultyLabel.setBounds(0, 200, 500, 30);
        add(difficultyLabel);
        
        // Difficulty Buttons
        Difficulty[] difficulties = Difficulty.values();
        difficultyButtons = new JButton[difficulties.length];
        
        for (int i = 0; i < difficulties.length; i++) {
            Difficulty diff = difficulties[i];
            JButton button = new JButton(diff.getName());
            button.setBounds(150, 250 + (i * 60), 200, 45);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setFocusPainted(false);
            
            if (diff == selectedDifficulty) {
                button.setBackground(new Color(16, 88, 9));
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(Color.YELLOW);
                button.setForeground(Color.BLACK);
            }
            
            button.addActionListener(e -> selectDifficulty(diff));
            difficultyButtons[i] = button;
            add(button);
        }
        
        // Start Button
        JButton startButton = new JButton("START GAME");
        startButton.setBounds(125, 500, 250, 60);
        startButton.setFont(new Font("Arial", Font.BOLD, 24));
        startButton.setBackground(new Color(16, 88, 9));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> gameManager.startGame(selectedDifficulty));
        add(startButton);
        
        // Instructions
        JLabel instructionsLabel = new JLabel("<html><center>Use Arrow Keys to move<br>Press ESC to return to menu</center></html>", SwingConstants.CENTER);
        instructionsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionsLabel.setForeground(Color.WHITE);
        instructionsLabel.setBounds(0, 450, 500, 40);
        add(instructionsLabel);
    }
    
    private void selectDifficulty(Difficulty difficulty) {
        selectedDifficulty = difficulty;
        
        // Update button colors
        Difficulty[] difficulties = Difficulty.values();
        for (int i = 0; i < difficulties.length; i++) {
            if (difficulties[i] == selectedDifficulty) {
                difficultyButtons[i].setBackground(new Color(16, 88, 9));
                difficultyButtons[i].setForeground(Color.BLACK);
            } else {
                difficultyButtons[i].setBackground (Color.YELLOW);
                difficultyButtons[i].setForeground(Color.WHITE);
            }
        }
    }
}
