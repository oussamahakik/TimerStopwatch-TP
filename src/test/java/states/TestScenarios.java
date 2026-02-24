package states;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import states.stopwatch.*;
import states.timer.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TestScenarios {

    private Context c;

    @Before
    public void setup() {
        c = new Context();
        // before each test, reset the values to avoid interference between tests
        AbstractTimer.resetInitialValues();
        AbstractStopwatch.resetInitialValues();
    }

    @Given("the timer is idle with empty memory")
    public void givenTimerIsIdleWithEmptyMemory() {
        assertSame(IdleTimer.Instance(), c.currentState);
        assertEquals(0, AbstractTimer.getTimer());
        assertEquals(0, AbstractTimer.getMemTimer());
    }

    @When("the user sets the timer memory to two seconds")
    public void whenUserSetsMemoryToTwoSeconds() {
        c.right(); // Idle -> SetTimer
        c.tick();  // memTimer = 1
        c.tick();  // memTimer = 2
        c.right(); // SetTimer -> Idle
    }

    @When("the user starts the timer")
    public void whenUserStartsTimer() {
        c.up(); // Idle -> ActiveTimer (RunningTimer)
        assertSame(RunningTimer.Instance(), c.currentState);
    }

    @Then("the timer reaches the ringing state after two ticks")
    public void thenTimerRingsAfterTwoTicks() {
        c.tick();
        assertEquals(1, AbstractTimer.getTimer());
        c.tick();
        assertSame(RingingTimer.Instance(), c.currentState);
        assertEquals(0, AbstractTimer.getTimer());
    }

    @Given("the stopwatch is running")
    public void givenStopwatchIsRunning() {
        c.left(); // Timer -> Stopwatch
        assertSame(ResetStopwatch.Instance(), c.currentState);
        c.up();   // Reset -> Running
        assertSame(RunningStopwatch.Instance(), c.currentState);
        c.tick(); // totalTime = 1
    }

    @When("the user presses split")
    public void whenUserPressesSplit() {
        c.up(); // Running -> Laptime
        assertSame(LaptimeStopwatch.Instance(), c.currentState);
        assertEquals(1, AbstractStopwatch.getLapTime());
    }

    @When("five ticks pass")
    public void whenFiveTicksPass() {
        c.tick();
        c.tick();
        c.tick();
        c.tick();
        c.tick();
    }

    @Then("the stopwatch returns automatically to running mode")
    public void thenStopwatchReturnsAutomaticallyToRunningMode() {
        assertSame(RunningStopwatch.Instance(), c.currentState);
    }

    @Given("the timer is paused with a configured memory")
    public void givenTimerIsPausedWithAConfiguredMemory() {
        whenUserSetsMemoryToTwoSeconds();
        whenUserStartsTimer();
        c.up(); // Running -> Paused
        assertSame(PausedTimer.Instance(), c.currentState);
    }

    @When("the user switches to stopwatch mode and starts it")
    public void whenUserSwitchesToStopwatchModeAndStartsIt() {
        c.left(); // Timer -> Stopwatch (history)
        assertSame(ResetStopwatch.Instance(), c.currentState);
        c.up();   // Reset -> Running
        assertSame(RunningStopwatch.Instance(), c.currentState);
    }

    @Then("switching back restores the paused timer history state")
    public void thenSwitchingBackRestoresThePausedTimerHistoryState() {
        c.left(); // Stopwatch -> Timer history
        assertSame(PausedTimer.Instance(), c.currentState);
    }
}
