package ru.practicum.event.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.model.dto.EventFullDto;
import ru.practicum.event.model.dto.UpdateEventAdminRequest;
import ru.practicum.event.service.EventService;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/events")
@RequiredArgsConstructor
@Validated
public class AdminEventController {

    private final EventService eventService;

    @GetMapping
    public List<EventFullDto> getEvents(
            @RequestParam(required = false) Set<Long> userIds,
            @RequestParam(required = false) Set<String> states,
            @RequestParam(required = false) Set<Long> categoryIds,
            @RequestParam(required = false) LocalDateTime rangeStart,
            @RequestParam(required = false) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "0") Long from,
            @RequestParam(defaultValue = "10") Long size
            ) {
        log.info("Admin: События (Поиск событий)");
        return eventService.getEvents(userIds, states, categoryIds, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("{eventId}")
    public EventFullDto updateAdminEvent(
            @PathVariable Long eventId,
            @RequestBody UpdateEventAdminRequest updEventAdm
            ) {
        log.info("Admin: События (Редактирование данных события и его статуса (отклонение/публикация)");
        return eventService.updateAdminEvent(eventId,updEventAdm);
    }

}
