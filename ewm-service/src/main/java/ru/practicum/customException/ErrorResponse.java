package ru.practicum.customException;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ErrorResponse {

    private final String status;

    private final String reason;

    private final String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp;

    public ErrorResponse(StatusException status, String message) {
        this.status = status.toString();
        switch (status) {
            case BAD_REQUEST:
                reason = "Incorrectly made request.";
                break;
            case NOT_FOUND:
                reason = "The required object was not found.";
                break;
            case CONFLICT:
                reason = "For the requested operation the conditions are not met.";
                break;
            default:
                reason = "Unknown status";
        }


        this.message = message;
        this.timestamp = LocalDateTime.now();
    }


    /*
    private final String error;
    private final String description;

    public ErrorResponse(String error, String description) {
        this.error = error;
        this.description = description;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }
*/
}
