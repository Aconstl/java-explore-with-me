package ru.practicum.request.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.model.dto.ParticipationRequestDto;
import ru.practicum.request.service.RequestService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/requests")
@RequiredArgsConstructor
@Validated
public class RequestController {

    private final RequestService requestService;

    @GetMapping
    public List<ParticipationRequestDto> getUserRequest(
            @PathVariable Long userId
    ) {
        log.info("Private: Запросы на участие (Получение информации о заявках текущего пользователя на участие в чужих событиях)");
        return requestService.getUserRequest(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequest(
            @PathVariable Long userId,
            @RequestParam Long eventId
    ) {
        log.info("Private: Запросы на участие (Добавление запроса от текущего пользователя на участие в событии)");
        ParticipationRequestDto requestDto = requestService.createRequest(userId,eventId);
        log.info("Private: Запрос на участие добавлен");
        return requestDto;
      //  return requestService.createRequest(userId,eventId);
    }

    @PostMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(
            @PathVariable Long userId,
            @PathVariable Long requestId
    ) {
        log.info("Private: Запросы на участие (Отмена своего запроса на участие в событии)");
        return requestService.cancelRequest(userId,requestId);
    }
}
