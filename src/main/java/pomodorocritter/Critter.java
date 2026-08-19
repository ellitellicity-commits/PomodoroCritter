package pomodorocritter;

public class Critter {
    private Mood mood; // Current Mood 
    private int completedSessions; // Completed Pomodoro Sessions 

    public Critter() { 
        this.mood = Mood.fromSessionCount(0);
        this.completedSessions = 0;
    } 

    public void react(int newSessionCount) { 
        this.completedSessions = newSessionCount; 
        this.mood = Mood.fromSessionCount(newSessionCount);
    } 

    public Mood getMood() { 
        return this.mood;
    }
}
