package com.github.slaout.immutability.exercise1.fixture;

import com.github.slaout.immutability.exercise1.domain.edit.Action;
import com.github.slaout.immutability.exercise1.domain.edit.Edit;
import com.github.slaout.immutability.exercise1.domain.edit.User;
import lombok.experimental.UtilityClass;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@UtilityClass
public class EditFixtures {

    public static final User ANY_USER = new User("someone", "Some One");

    public static final Instant NOW = Instant.parse("2022-04-26T10:30:00Z");
    public static final Instant ANY_INSTANT = Instant.now();
    public static final Clock ALWAYS_NOW = Clock.fixed(NOW, ZoneId.systemDefault());

    public static final Action ANY_ACTION = Action.EDITION;

    public static final Edit ANY_EDIT = Edit.restoreFromDatabase(ANY_USER, ANY_INSTANT, ANY_ACTION);

}