package pomodorocritter;

// 4 Moods in Check! 
public enum Mood {
    SLEEPY, NEUTRAL, HAPPY, GLOWING; 

    public static Mood fromSessionCount(int completedSessions) { 
        if (completedSessions == 0) {
            return Mood.SLEEPY;
        } 
        else if (completedSessions == 1 || completedSessions == 2) {
            return Mood.NEUTRAL;
        } 
        else if (completedSessions == 3 || completedSessions == 4) { 
            return Mood.HAPPY;
        } 
        else { 
            return Mood.GLOWING;
        }
    } 

    // Temp Main Method 
    public static void main(String[] args) { 
        System.out.println(Mood.fromSessionCount(3)); // Should be Happy! 
    }
}



