package states;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import states.stopwatch.AbstractStopwatch;
import states.stopwatch.LaptimeStopwatch;
import states.stopwatch.ResetStopwatch;
import states.stopwatch.RunningStopwatch;
import states.timer.AbstractTimer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * @author: oussama hakik
 */


class StopwatchStateCoverageTests {

    private Context c;

    @BeforeEach
    void setup() {
        c = new Context();
        AbstractTimer.resetInitialValues();
        AbstractStopwatch.resetInitialValues();
    }

    @Test
    @DisplayName("RunningStopwatch right transitions to ResetStopwatch")
    void runningRightTransitionsToReset() {
        c.left(); // Timer => Stopwatch
        c.up(); // ResetStopwatch => RunningStopwatch
        c.tick(); // totalTime = 1
        assertSame(RunningStopwatch.Instance(), c.currentState);
        assertEquals(1, AbstractStopwatch.getTotalTime());

        c.right(); // RunningStopwatch => ResetStopwatch
        assertSame(ResetStopwatch.Instance(), c.currentState);
        assertEquals(0, AbstractStopwatch.getLapTime());
    }

    @Test
    @DisplayName("LaptimeStopwatch up unsplits to RunningStopwatch")
    void laptimeUpReturnsToRunning() {
        c.left(); // Timer -> Stopwatch
        c.up(); // Reset -> Running
        c.tick(); // totalTime = 1
        c.up(); // Running -> Laptime
        assertSame(LaptimeStopwatch.Instance(), c.currentState);

        c.up(); // Laptime -> Running (unsplit)
        assertSame(RunningStopwatch.Instance(), c.currentState);
    }

    @Test
    @DisplayName("LaptimeStopwatch auto-returns to RunningStopwatch after timeout")
    void laptimeTimeoutReturnsToRunning() {
        c.left(); // Timer -> Stopwatch
        c.up(); // Reset -> Running
        c.tick(); // totalTime = 1
        c.up(); // Running -> Laptime (lapTime should become 1 on entry)
        assertSame(LaptimeStopwatch.Instance(), c.currentState);
        assertEquals(1, AbstractStopwatch.getLapTime());

        c.tick();
        c.tick();
        c.tick();
        c.tick();
        c.tick(); // timeout == 0 -> Running
        assertSame(RunningStopwatch.Instance(), c.currentState);
    }
}
