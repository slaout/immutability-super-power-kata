package com.github.slaout.immutability.exercise1.domain.edit;

import lombok.Value;

import java.util.Objects;

@Value(staticConstructor = "of")
public class Editable<T> {
    T value;
    Edit lastEdit;

    private Editable(T value, Edit lastEdit) {
        this.value = Objects.requireNonNull(value, "value");
        this.lastEdit = Objects.requireNonNull(lastEdit, "lastEdit");
    }

    public Editable<T> withEditedValue(T editedValue, Edit edit) {
        if (this.value.equals(editedValue)) {
            return this;
        }

        return Editable.of(editedValue, edit);
    }

    public Editable<T> withSyncedValue(T syncedValue) {
        return new Editable<>(syncedValue, this.lastEdit);
    }

    public boolean hasValue(T otherValue) {
        return value.equals(otherValue);
    }
}
