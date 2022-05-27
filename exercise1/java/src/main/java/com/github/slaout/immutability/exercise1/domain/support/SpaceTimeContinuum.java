package com.github.slaout.immutability.exercise1.domain.support;

import lombok.experimental.UtilityClass;

import java.time.Clock;

@UtilityClass
public class SpaceTimeContinuum {

    // TODO ThreadLocal + @AfterEach forgetTestingClock() { clock = DEFAULT_CLOCK; } in base test-class?
    private static Clock clock = Clock.systemDefaultZone();

    // Not dependency-injected, to reduce code clutter for this thing that should only change for tests
    public static Clock clock() {
        return clock;
    }

    // Package-private to reduce the likelihood of production code to change it.
    static void setTestingClock(Clock clock) {
        SpaceTimeContinuum.clock = clock;
    }

}
