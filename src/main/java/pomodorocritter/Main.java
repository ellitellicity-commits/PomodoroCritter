package pomodorocritter;

import javax.swing.Timer;
import java.awt.EventQueue;

public class Main {

    public static void main(String[] args) {

        // I. Instances (same as before)
        Critter myCritter = new Critter();
        SessionLog mySessionLog = new SessionLog();
        int sessionLengthSeconds = 5; // test value, swap to 1500 for a real 25-minute pomodoro
        PomodoroTimer myPomodoroTimer = new PomodoroTimer(sessionLengthSeconds);

        // II. The window
        PomodoroCritterFrame frame = new PomodoroCritterFrame();

        // III. Button wiring
        frame.getStartButton().addActionListener(e -> myPomodoroTimer.start());
        frame.getPauseButton().addActionListener(e -> myPomodoroTimer.pause());

        // IV. Tick loop, same shape as your original Timer setup
        Timer swingTimer = new Timer(1000, e -> {
            myPomodoroTimer.tick();

            int remaining = myPomodoroTimer.getRemainingSeconds();
            int minutes = remaining / 60;
            int seconds = remaining % 60;
            frame.getTimerLabel().setText(String.format("%02d:%02d", minutes, seconds));

            if (myPomodoroTimer.isComplete()) {
                mySessionLog.logSession();
                myCritter.react(mySessionLog.getCount());

                frame.getMoodLabel().setText("Mood: " + myCritter.getMood());
                frame.getSessionCountLabel().setText("Sessions: " + mySessionLog.getCount());
                frame.getCritterPanel().setMood(myCritter.getMood());

                myPomodoroTimer.pause();
            }
        });

        swingTimer.start();

        EventQueue.invokeLater(() -> frame.setVisible(true));
    }
}
