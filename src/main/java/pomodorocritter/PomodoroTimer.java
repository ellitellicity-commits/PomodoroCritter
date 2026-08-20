package pomodorocritter;

public class PomodoroTimer {
    private int remainingSeconds; // Total Seconds for the Timer 
    private int initialSeconds; // Holds the Original Time set by the User 
    private boolean isRunning; // Timer Running State
    
    public PomodoroTimer(int startingSeconds) { 
        this.remainingSeconds = startingSeconds; 
        this.initialSeconds = startingSeconds; 
        this.isRunning = false;
    } 

    // Part 1: Simple Functions 
    public void start() { 
        this.isRunning = true;
    }

    public void pause() { 
        this.isRunning = false;
    }  

    public void reset() { 
        this.remainingSeconds = initialSeconds;
    }

    public void tick() { 
        if (this.isRunning == true && this.remainingSeconds > 0) { 
            this.remainingSeconds -= 1;
        }
    } 

    public boolean isComplete() { 
        return this.remainingSeconds == 0; // Once seconds hit 00:00! 
    } 

    public int getRemainingSeconds() { 
        return this.remainingSeconds;
    }

    // Part 2: Dynamic Sessions and Reset + Start 
    public void resetTo(int seconds) { 
        this.remainingSeconds = seconds; 

    }
}
