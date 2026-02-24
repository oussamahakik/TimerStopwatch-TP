package states;

import java.util.logging.Logger;

public abstract class ClockState {

    private static final Logger logger = Logger.getLogger(ClockState.class.getName());
	
    public abstract ClockState left(); // button 1 pressed
    public String getLeftText() { return "change mode"; } // text to display on button 1

    public ClockState up() { return this; } // button 2 pressed
    public String getUpText() { return "(unused)"; }

    public ClockState right() {return this; } // button 3 pressed (by default do nothing)
    public String getRightText() { return "(unused)"; }; // text to display on button 3
        
    public abstract String getDisplayString(); // string to be displayed in GUI
    public abstract Mode getMode(); 
    
    // transition can only be used, but not overridden, by substates
    protected final ClockState transition(ClockState nextState) {
    	exit(); // execute the exit action of the current state;
    	ClockState target = nextState;
    	target.entry(); // execute the entry action of the target state;
    	//target.doIt(); // executing the recurring action in the target state once;
        return target; // and return the target state
        // 
    }
    
    // entry and exit and do actions can be redefined by, and are only visible to, substates
    protected void entry() {
    	// the entry action of the state, which is empty (no action) by default
        //replace System.out by a logger
        logger.fine("entering {} " + this.getClass().getName());

    };
    	
    protected void exit() {
    	// the exit action of the state, which is empty (no action) by default
        logger.fine("exiting {} " + this.getClass().getName());
    };
    	
    protected ClockState doIt() {
    	// specific behaviour to be implemented in each state.
    	// Will be called on each tick()
    	return this; } 
       
}
