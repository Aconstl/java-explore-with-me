package ru.practicum.customException.model;

public class BadRequestException extends  RuntimeException { //ошибка 400
    public BadRequestException(String str) {
        super(str);
    }
}
