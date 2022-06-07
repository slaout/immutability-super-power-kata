package com.github.slaout.immutability.exercise1.fixture;

import com.github.slaout.immutability.exercise1.domain.edit.Action;
import com.github.slaout.immutability.exercise1.domain.edit.Edit;
import com.github.slaout.immutability.exercise1.domain.edit.User;
import lombok.experimental.UtilityClass;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class EditFixtures {

    public static User anyUser() {
        return new User("someone", "Some One");
    }

    public static final Instant NOW = Instant.parse("2022-04-26T10:30:00Z");
    public static final Instant ANY_INSTANT = Instant.now();
    public static final Clock ALWAYS_NOW = Clock.fixed(NOW, ZoneId.systemDefault());

    public static final Action ANY_ACTION = Action.EDITION;

    public static Edit anyEdit() {
        return Edit.restoreFromDatabase(anyUser(), ANY_INSTANT, ANY_ACTION);
    }

    public static final Edit SOME_EDIT = Edit.restoreFromDatabase(
            new User("last-login", "Last Full Name"),
            Instant.now().minus(1, ChronoUnit.DAYS),
            Action.CREATION);

}
