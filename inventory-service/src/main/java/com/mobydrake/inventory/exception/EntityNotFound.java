package com.mobydrake.inventory.exception;

import org.jspecify.annotations.NonNull;

public class EntityNotFound extends RuntimeException {

    private static final String MSG = "Entity not found";

    public EntityNotFound() {
        super(MSG);
    }

    public EntityNotFound(@NonNull String message) {
        super(message);
    }
}
