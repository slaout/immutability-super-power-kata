package com.github.slaout.immutability.exercise1.domain.edit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Edit {
    private User user;
    private Instant instant;
    private Action action;
}
