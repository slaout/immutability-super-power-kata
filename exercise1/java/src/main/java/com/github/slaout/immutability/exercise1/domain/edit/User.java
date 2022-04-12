package com.github.slaout.immutability.exercise1.domain.edit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    String login;
    String fullName;
}
