package com.github.slaout.immutability.exercise1.domain.edit;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.Instant;

import static com.github.slaout.immutability.exercise1.domain.support.SpaceTimeContinuum.clock;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Edit {
    User user;
    Instant instant;
    Action action;

    public static Edit now(User user, Action action) {
        return new Edit(user, Instant.now(clock()), action);
    }

    public static Edit restoreFromDatabase(User user, Instant instant, Action action) {
        return new Edit(user, instant, action);
    }
}
