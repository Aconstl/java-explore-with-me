package ru.practicum.customException.model;

public class ConflictException extends RuntimeException { //ошибка 409
    public ConflictException(String message) {
        super(message);
    }
}

