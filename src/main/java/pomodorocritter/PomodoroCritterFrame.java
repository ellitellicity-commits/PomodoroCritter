// Create the Window for Pomodoro Timer (Vibe-Coded Frontend)
package pomodorocritter;

import javax.swing.*;
import java.awt.*;

public class PomodoroCritterFrame extends JFrame {

    private CritterPanel critterPanel;
    private JLabel timerLabel;
    private JLabel moodLabel;
    private JButton startButton;
    private JButton pauseButton;
    private JLabel sessionCountLabel;

    public PomodoroCritterFrame() {
        setTitle("Pomodoro Critter");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        critterPanel = new CritterPanel();
        critterPanel.setPreferredSize(new Dimension(380, 250));
        add(critterPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        timerLabel = new JLabel("05:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        moodLabel = new JLabel("Mood: SLEEPY", SwingConstants.CENTER);
        moodLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        moodLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(timerLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(moodLabel);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        startButton = new JButton("Start");
        pauseButton = new JButton("Pause");
        sessionCountLabel = new JLabel("Sessions: 0");

        bottomPanel.add(startButton);
        bottomPanel.add(pauseButton);
        bottomPanel.add(sessionCountLabel);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Exposed so Main can attach listeners and update text during the wiring step
    public JButton getStartButton() { return startButton; }
    public JButton getPauseButton() { return pauseButton; }
    public JLabel getTimerLabel() { return timerLabel; }
    public JLabel getMoodLabel() { return moodLabel; }
    public JLabel getSessionCountLabel() { return sessionCountLabel; }
    public CritterPanel getCritterPanel() { return critterPanel; }
}
