package ru.practicum.event.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.model.dto.UpdateEventUserRequest;
import ru.practicum.event.model.dto.EventFullDto;
import ru.practicum.event.model.dto.EventShortDto;
import ru.practicum.event.model.dto.NewEventDto;
import ru.practicum.event.model.requestStatus.EventRequestStatusUpdateRequest;
import ru.practicum.event.model.requestStatus.EventRequestStatusUpdateResult;
import ru.practicum.request.model.dto.ParticipationRequestDto;
import ru.practicum.event.service.EventService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/event")
@RequiredArgsConstructor
@Validated
public class PrivateEventController {

    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getUserEvents(
        @PathVariable Long userId,
        @RequestParam(defaultValue = "0") Long from,
        @RequestParam(defaultValue = "10") Long size
    ) {
        log.debug("Private: События (Получение событий, добавленных текущим пользователем)");
        return eventService.getUserEvents(userId,from,size);
    }

    @PostMapping
    public EventFullDto createEvent(
         @PathVariable Long userId,
         @RequestBody @Valid NewEventDto newEvent
    ) {
        log.debug("Private: События (Добавление нового пользователя)");
        return eventService.createEvent(userId, newEvent);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getUserEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId
    ) {
        log.debug("Private: События (Получение полной информации о событии добавленном текущим пользователем)");
        return eventService.getUserEvent(userId,eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody @Valid UpdateEventUserRequest updateEvent
    ) {
        log.debug("Private: События (Изменение события добавленного текущим пользователем)");
        return eventService.updateEvent(userId,eventId, updateEvent);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult changeStatusEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody @Valid EventRequestStatusUpdateRequest statusUpdateRequest
    ) {
        log.debug("Private: События (Изменения статуса(подтверждена,отменена) заявок на участие в событии текущего пользователя");
        return eventService.changeStatusEvent(userId,eventId,statusUpdateRequest);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsInUserEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId
            ) {
        log.debug("Private: События (Получение информации о запросах на участие в событии текущего пользователя)");
        return eventService.getRequestsInUserEvent(userId,eventId);
    }

}