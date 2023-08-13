package ru.practicum.customException;

public enum StatusException {

    NOT_FOUND("404 NOT_FOUND"),
    BAD_REQUEST("400 BAD_REQUEST"),
    CONFLICT("409 CONFLICT"),
    INTERNAL_SERVER_ERROR("500 INTERNAL_SERVER_ERROR");
    private final String status;

    StatusException(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
