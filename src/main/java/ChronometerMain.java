import gui.SwingGUI;
import states.Context;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ChronometerMain {

    private static final Logger logger = Logger.getLogger(ChronometerMain.class.getName());
	private SwingGUI g;
	private Context c;

    void processSingleCycle() {
        g.updateUI(c);
        c.tick();
    }

    // The method run() ensures that with a given frequency
    // the state machine's actions are executed with tick() and
    // the ui is updated accordingly with updateUIText().
	private void run(int frequency) {
        // infinite loop that asks the current state to do whatever it needs to do
        // and that updates the graphical user interface accordingly
 		  g.updateUI(c);
    	  while (true) {
    		try {
                Thread.sleep(frequency);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.log(Level.WARNING, "Chronometer loop interrupted", e);
                break;
            }
            processSingleCycle();
  	      }
      }
    
    public static void main(String[] args) {
        ChronometerMain myChrono = new ChronometerMain(); // create an instance of Chronometer;
        myChrono.c = new Context(); // create the state machine context
        myChrono.g = new SwingGUI(myChrono.c); // create the GUI and pass it the context
        
        myChrono.run(100); // and start running with frequency of 100 millisecs
        
    }
   
}
