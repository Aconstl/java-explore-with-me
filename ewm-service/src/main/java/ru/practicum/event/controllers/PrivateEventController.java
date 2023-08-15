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
        log.info("Private: События (Получение событий, добавленных пользователем с id {})", userId);
        List<EventShortDto> events = eventService.getUserEvents(userId,from,size);
        log.info("Private: События пользователя с id {} получены успешно", userId);
        return events;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(
         @PathVariable Long userId,
         @RequestBody @Valid NewEventDto newEvent
    ) {
        log.info("Private: События (Добавление нового события пользователем с id {})", userId);
        EventFullDto eventFullDto = eventService.createEvent(userId, newEvent);
        log.info("Private: Событие добавлено успешно пользователем с id {}", userId);
        return eventFullDto;
    }

    @GetMapping("/{eventId}")
    public EventFullDto getUserEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId
    ) {
        log.info("Private: События (Получение полной информации о событии под id {} добавленным пользователем с id {})", eventId,userId);
        return eventService.getUserEvent(userId,eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody @Valid UpdateEventUserRequest updateEvent
    ) {
        log.info("Private: События (Изменение события c id {} добавленного пользователем с id {})", eventId,userId);
        EventFullDto eventFullDto = eventService.updateEvent(userId,eventId, updateEvent);
        log.info("Private: Изменения события № {} пользователем c id {} выполнено успешно", eventId,userId);
        return eventFullDto;
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult changeStatusEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId,
            @RequestBody @Valid EventRequestStatusUpdateRequest statusUpdateRequest
    ) {
        log.info("Private: События (Изменения статуса(подтверждена,отменена) заявок на участие в событии № {} текущего пользователя с id {})",eventId,userId);
        EventRequestStatusUpdateResult result = eventService.changeStatusEvent(userId,eventId,statusUpdateRequest);
        log.info("Изменение статусов заявок события № {} выполнено успешно", eventId);
        return result;
    }

    @GetMapping("/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsInUserEvent(
            @PathVariable Long userId,
            @PathVariable Long eventId
            ) {
        log.info("Private: События (Получение информации о запросах на участие в событии № {} текущего пользователя с id {})",eventId,userId);
        return eventService.getRequestsInUserEvent(userId,eventId);
    }

}
