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
        log.info("Private: Запросы на участие (Получение информации о заявках текущего пользователя c id {} на участие в чужих событиях)",userId);
        return requestService.getUserRequest(userId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createRequest(
            @PathVariable Long userId,
            @RequestParam Long eventId
    ) {
        log.info("Private: Запросы на участие (Добавление запроса от текущего пользователя c id {} на участие в событии № {})", userId,eventId);
        ParticipationRequestDto requestDto = requestService.createRequest(userId,eventId);
        log.info("Private: Запрос на участие в событии № {} пользователем c id {} добавлен", eventId,userId);
        return requestDto;
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto cancelRequest(
            @PathVariable Long userId,
            @PathVariable Long requestId
    ) {
        log.info("Private: Запросы на участие (Отмена своего запроса № {} пользователя c id {} на участие в событии )",requestId, userId);
        ParticipationRequestDto requestDto = requestService.cancelRequest(userId,requestId);
        log.info("Private: Запрос № {} пользователя c id {} на участие в событии отменен)",requestId, userId);
        return requestDto;
    }
}
