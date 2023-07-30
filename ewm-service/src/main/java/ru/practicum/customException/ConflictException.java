package ru.practicum.customException;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}

