package ru.practicum.event.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.model.dto.EventFullDto;
import ru.practicum.event.model.dto.EventShortDto;
import ru.practicum.event.model.SortEvent;
import ru.practicum.event.service.EventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
@Validated
public class PublicEventController {

    private final EventService eventService;

    @GetMapping
    public List<EventShortDto> getEventWithFilter(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) Set<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) LocalDateTime rangeStart,
            @RequestParam(required = false) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) SortEvent sort,
            @RequestParam(required = false) Long from,
            @RequestParam(required = false) Long size
            ) {
        log.debug("Public: События (Получение событий с возможностью фильтрации)");
        return eventService.getEventWithFilter(text,categories,paid,rangeStart,rangeEnd,onlyAvailable,sort,from,size);
    }

    @GetMapping("/id")
    public EventFullDto getEventById(@PathVariable Long id) {
        log.debug("Public: События (Получение подробной информации об опубикованном событии по его идентификатору)");
        return eventService.getEventById(id);
    }


}
