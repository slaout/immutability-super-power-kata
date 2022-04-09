package com.github.slaout.immutability.exercise1.domain.edit;

import lombok.Value;

import java.time.Instant;

@Value
public class Edit {
    User user;
    Instant instant;
    Action action;
}
