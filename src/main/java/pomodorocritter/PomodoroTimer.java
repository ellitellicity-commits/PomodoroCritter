package pomodorocritter;

public class PomodoroTimer {
    private int remainingSeconds; // Total Seconds for the Timer 
    private boolean isRunning; // Timer Running State
    
    public PomodoroTimer(int startingSeconds) { 
        this.remainingSeconds = startingSeconds; 
        this.isRunning = false;
    } 

    public void start() { 
        this.isRunning = true;
    }

    public void pause() { 
        this.isRunning = false;
    } 

    public void tick() { 
        if (this.isRunning == true && this.remainingSeconds > 0) { 
            this.remainingSeconds -= 1;
        }
    } 

    public boolean isComplete() { 
        return this.remainingSeconds == 0; // Once seconds hit 00:00! 
    }
}
