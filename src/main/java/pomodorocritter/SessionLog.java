package pomodorocritter;

public class SessionLog {
    private int counter; // Counts Sessions 

    public SessionLog() { 
        this.counter = 0; 
    } 

    public void logSession() { 
        this.counter += 1;
    } 

    public int getCount() { 
        return this.counter;
    }
}
