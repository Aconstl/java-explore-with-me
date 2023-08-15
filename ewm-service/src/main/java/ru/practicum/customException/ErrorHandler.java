package ru.practicum.customException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.customException.model.BadRequestException;
import ru.practicum.customException.model.ConflictException;
import ru.practicum.customException.model.NotFoundException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundException(final NotFoundException e) {
        log.error("Ошибка 404 : {}", e.getMessage());
        return new ErrorResponse(StatusException.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse conflictException(final ConflictException e) {
        log.error("Ошибка 409 : {}", e.getMessage());
        return new ErrorResponse(StatusException.CONFLICT, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestException(final BadRequestException e) {
        log.error("Ошибка 400 : {}", e.getMessage());
        return new ErrorResponse(StatusException.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestException(final MethodArgumentNotValidException e) {
        log.error("Ошибка 400 : {}", e.getMessage());
        return new ErrorResponse(StatusException.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestException(final ConstraintViolationException e) {
        log.error("Ошибка 400 : {}", e.getMessage());
        return new ErrorResponse(StatusException.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestException(final MissingServletRequestParameterException e) {
        log.error("Ошибка 400 : {}", e.getMessage());
        return new ErrorResponse(StatusException.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse badRequestException(final Exception e) {
        log.error("Ошибка 500 : {}", e.getMessage());
        return new ErrorResponse(StatusException.INTERNAL_SERVER_ERROR, e.getMessage());
    }

}
