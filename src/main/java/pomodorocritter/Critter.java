package pomodorocritter;

public class Critter {
    private Mood mood; // Current Mood
    private int completedSessions; // Completed Pomodoro Sessions

    public Critter() {
        this.mood = Mood.fromMinutes(0);
        this.completedSessions = 0;
    }

    public void react(int newSessionCount, int newTotalMinutes) {
        this.completedSessions = newSessionCount;
        this.mood = Mood.fromMinutes(newTotalMinutes);
    }

    public Mood getMood() { 
        return this.mood;
    }
}
