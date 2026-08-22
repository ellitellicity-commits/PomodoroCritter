package pomodorocritter;

public class SessionLog {
    private int counter; // Counts qualifying sessions
    private int totalMinutes; // Cumulative minutes from qualifying sessions

    public SessionLog() {
        this.counter = 0;
        this.totalMinutes = 0;
    }

    public void logSession(int minutes) {
        this.counter += 1;
        this.totalMinutes += minutes;
    }

    public int getCount() {
        return this.counter;
    }

    public int getTotalMinutes() {
        return this.totalMinutes;
    }
}
