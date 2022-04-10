package com.github.slaout.immutability.exercise1.domain.edit;

import lombok.Value;

import java.util.Objects;

@Value(staticConstructor = "of")
public class NullableEditable<T> {
    T value;
    Edit lastEdit;

    public static <T> NullableEditable<T> withEditedValue(NullableEditable<T> initialValue, T editedValue, Edit edit) {
        if (hasValue(initialValue, editedValue)) {
            return initialValue;
        }

        return NullableEditable.of(editedValue, edit);
    }

    public static <T> boolean hasValue(NullableEditable<T> nullableEditable, T otherValue) {
        if (nullableEditable == null) {
            return otherValue == null;
        }

        return nullableEditable.value.equals(otherValue);
    }

    private NullableEditable(T value, Edit lastEdit) {
        this.value = value;
        this.lastEdit = Objects.requireNonNull(lastEdit, "lastEdit");
    }
}
