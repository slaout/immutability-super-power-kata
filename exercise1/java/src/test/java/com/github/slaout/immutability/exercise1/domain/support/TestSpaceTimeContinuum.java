package com.github.slaout.immutability.exercise1.domain.support;

import lombok.experimental.UtilityClass;

import java.time.Clock;

@UtilityClass
public class TestSpaceTimeContinuum {

    public static void givenClockIs(Clock clock) {
        SpaceTimeContinuum.setTestingClock(clock);
    }

}
