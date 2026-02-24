package states.timer;

import states.ClockState;

public class RingingTimer extends ActiveTimer {
 	
	// use Singleton design pattern
	private RingingTimer() {}; // make constructor invisible to clients
    private static RingingTimer instance = null;
    public static RingingTimer Instance() {
        if(instance == null) instance = new RingingTimer();        
        return instance;
    }
    
    @Override
    public ClockState doIt() {
    	java.awt.Toolkit.getDefaultToolkit().beep();
    	return this;
    }

	@Override
	protected void entry() {
		ringing = true;
	}

	@Override
	protected void exit() {
		ringing = false;
	}
    
    public String getDisplayString() {
    	// display decreasing values starting from memTimer counting down to 0
        return "Time's up !";
    }
    
}
