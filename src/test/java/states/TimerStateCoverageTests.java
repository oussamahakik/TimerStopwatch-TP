package states;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import states.timer.AbstractTimer;
import states.timer.IdleTimer;
import states.timer.RingingTimer;
import states.timer.RunningTimer;
import states.timer.SetTimer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author : oussama hakik
 */

class TimerStateCoverageTests {

    private Context c;

    @BeforeEach
    void setup() {
        c = new Context();
        AbstractTimer.resetInitialValues();
        c.currentState = IdleTimer.Instance();
    }

    @Test
    @DisplayName("SetTimer left resets memory and stays in SetTimer")
    void setTimerLeftResetsMemory() {
        c.right(); // Idle -> SetTimer
        c.tick(); // memTimer = 1
        assertSame(SetTimer.Instance(), c.currentState);
        assertEquals(1, AbstractTimer.getMemTimer());

        c.left(); // reset memory in SetTimer
        assertSame(SetTimer.Instance(), c.currentState);
        assertEquals(0, AbstractTimer.getMemTimer());
    }

    @Test
    @DisplayName("SetTimer up increments by 5 and tick increments by 1")
    void setTimerUpAndTickIncrementMemory() {
        c.right(); // Idle -> SetTimer
        c.up(); // +5
        c.tick(); // +1
        assertSame(SetTimer.Instance(), c.currentState);
        assertEquals(6, AbstractTimer.getMemTimer());
    }

    @Test
    @DisplayName("Ringing flag toggles on Ringing entry and exit")
    void ringingFlagTogglesOnEntryAndExit() {
        c.right(); // Idle -> SetTimer
        c.tick(); // memTimer = 1
        c.right(); // SetTimer -> Idle
        c.up(); // Idle -> Running with timer = 1
        assertSame(RunningTimer.Instance(), c.currentState);

        c.tick(); // Running -> Ringing because timer reaches 0
        assertSame(RingingTimer.Instance(), c.currentState);
        assertTrue(AbstractTimer.isRinging());

        c.right(); // ActiveTimer right -> Idle
        assertSame(IdleTimer.Instance(), c.currentState);
        assertFalse(AbstractTimer.isRinging());
    }
}
