package pomodorocritter;

// 4 Moods in Check!
public enum Mood {
    SLEEPY, NEUTRAL, HAPPY, GLOWING;

    private static final int NEUTRAL_THRESHOLD_MINUTES = 15;
    private static final int HAPPY_THRESHOLD_MINUTES = 45;
    private static final int GLOWING_THRESHOLD_MINUTES = 75;

    public static Mood fromMinutes(int totalMinutes) {
        if (totalMinutes < NEUTRAL_THRESHOLD_MINUTES) {
            return Mood.SLEEPY;
        }
        else if (totalMinutes < HAPPY_THRESHOLD_MINUTES) {
            return Mood.NEUTRAL;
        }
        else if (totalMinutes < GLOWING_THRESHOLD_MINUTES) {
            return Mood.HAPPY;
        }
        else {
            return Mood.GLOWING;
        }
    }
}



