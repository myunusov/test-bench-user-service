package ru.iunusov.testbench.users.service;

public final class NotFoundException extends RuntimeException {

    public NotFoundException(final String message) {
        super(message);
    }
}
