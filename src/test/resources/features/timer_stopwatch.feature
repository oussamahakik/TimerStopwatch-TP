Feature: Timer and Stopwatch core behaviors

  Scenario: Timer reaches ringing after a configured countdown
    Given the timer is idle with empty memory
    When the user sets the timer memory to two seconds
    And the user starts the timer
    Then the timer reaches the ringing state after two ticks

  Scenario: Stopwatch split mode exits automatically after timeout
    Given the stopwatch is running
    When the user presses split
    And five ticks pass
    Then the stopwatch returns automatically to running mode

  Scenario: History state is restored when switching modes
    Given the timer is paused with a configured memory
    When the user switches to stopwatch mode and starts it
    Then switching back restores the paused timer history state
