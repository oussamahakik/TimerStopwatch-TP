package gui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import states.Context;
import states.stopwatch.ResetStopwatch;
import states.timer.IdleTimer;
import states.timer.SetTimer;

import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * @author :  oussama hakik
 */

class HeadlessGUIEventsTest {

    private Context c;
    private HeadlessGUI g;

    @BeforeEach
    void setup() {
        c = new Context();
        g = new HeadlessGUI(c);
    }

    @Test
    @DisplayName("Button 1 click triggers left event")
    void button1TriggersLeft() {
        assertSame(IdleTimer.Instance(), c.currentState);
        g.b1.doClick();
        assertSame(ResetStopwatch.Instance(), c.currentState);
    }

    @Test
    @DisplayName("Button 2 click triggers up event")
    void button2TriggersUp() {
        c.right(); // transition to SetTimer
        assertSame(SetTimer.Instance(), c.currentState);
        g.b2.doClick(); // button 2 does not trigger any event in SetTimer, so we should remain in SetTimer
        assertSame(SetTimer.Instance(), c.currentState);
    }

    @Test
    @DisplayName("Button 3 click triggers right event")
    void button3TriggersRight() {
        assertSame(IdleTimer.Instance(), c.currentState);
        g.b3.doClick();
        assertSame(SetTimer.Instance(), c.currentState);
    }
}
