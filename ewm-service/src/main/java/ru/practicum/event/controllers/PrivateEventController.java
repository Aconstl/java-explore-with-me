package ru.practicum.event.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/users/{userId}/events")
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
        log.info("Private: События (Получение событий, добавленных текущим пользователем)");
        List<EventShortDto> events = eventService.getUserEvents(userId,from,size);
        log.info("Private: События пользователя получены успешно");
        return events;
       // return eventService.getUserEvents(userId,from,size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(
         @PathVariable Long userId,
         @RequestBody @Valid NewEventDto newEvent
    ) {
        log.info("Private: События (Добавление нового события)");
        EventFullDto eventFullDto = eventService.createEvent(userId, newEvent);
        log.info("Private: Событие добавлено успешно ");
        return eventFullDto;
       // return eventService.createEvent(userId, newEvent);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getUserEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId
    ) {
        log.info("Private: События (Получение полной информации о событии добавленном текущим пользователем)");
        return eventService.getUserEvent(userId,eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody @Valid UpdateEventUserRequest updateEvent
    ) {
        log.info("Private: События (Изменение события добавленного текущим пользователем)");
        EventFullDto eventFullDto = eventService.updateEvent(userId,eventId, updateEvent);
        log.info("Private: Изменения события пользователем выполнено");
        return eventFullDto;
       // return eventService.updateEvent(userId,eventId, updateEvent);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult changeStatusEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody @Valid EventRequestStatusUpdateRequest statusUpdateRequest
    ) {
        log.info("Private: События (Изменения статуса(подтверждена,отменена) заявок на участие в событии текущего пользователя");
        EventRequestStatusUpdateResult result = eventService.changeStatusEvent(userId,eventId,statusUpdateRequest);
        log.info("Изменение статусов заявок выполнено успешно");
        return result;
       // return eventService.changeStatusEvent(userId,eventId,statusUpdateRequest);
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsInUserEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId
            ) {
        log.info("Private: События (Получение информации о запросах на участие в событии текущего пользователя)");
        return eventService.getRequestsInUserEvent(userId,eventId);
    }

}
