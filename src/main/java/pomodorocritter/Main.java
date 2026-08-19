package pomodorocritter;

import javax.swing.Timer;

public class Main {

    public static void main(String[] args) throws InterruptedException { 

        // I. Instances 
        Critter myCritter = new Critter(); 
        SessionLog mySessionLog = new SessionLog(); 
        PomodoroTimer myPomodoroTimer = new PomodoroTimer(5); 

        // II. Session Process 
        /* mySessionLog.logSession();
        myCritter.react(mySessionLog.getCount());     
        System.out.println(myCritter.getMood()); */

        // III. Timer Setup (NEW!) 
        Timer myTimer = new Timer(1000, e -> { 
            myPomodoroTimer.tick(); 

            if (myPomodoroTimer.isComplete()) { 
                mySessionLog.logSession(); 
                myCritter.react(mySessionLog.getCount()); 
                System.out.println(myCritter.getMood());     
                myPomodoroTimer.pause();
            } 

        }); 

        myPomodoroTimer.start();
        myTimer.start();
        Thread.sleep(6000);

    }

}
