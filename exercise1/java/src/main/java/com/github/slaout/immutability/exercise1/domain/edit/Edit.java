package com.github.slaout.immutability.exercise1.domain.edit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Edit extends User {
    private Instant instant;
    private Action action;
}
