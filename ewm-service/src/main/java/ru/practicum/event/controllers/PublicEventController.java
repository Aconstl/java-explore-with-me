package ru.practicum.event.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.HitClient;
import ru.practicum.event.model.dto.EventFullDto;
import ru.practicum.event.model.dto.EventShortDto;
import ru.practicum.event.model.SortEvent;
import ru.practicum.event.service.EventService;
import ru.practicum.port.StatisticPort;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class PublicEventController {

    private final EventService eventService;
    private final HitClient stats;
    private final StatisticPort statisticPort;

    @GetMapping
    public List<EventShortDto> getEventWithFilter(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) Set<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) SortEvent sort,
            @RequestParam(defaultValue = "0") Long from,
            @RequestParam(defaultValue = "10") Long size,
            HttpServletRequest request
            ) {
        log.info("Public: События (Получение событий с возможностью фильтрации)");
      // stats.newHit("ewm-main-service",request.getRequestURI(),request.getRemoteAddr(),LocalDateTime.now());
        statisticPort.newHit(request.getRequestURI(),request.getRemoteAddr());
        return eventService.getEventWithFilter(text,categories,paid,rangeStart,rangeEnd,onlyAvailable,sort,from,size);
    }

    @GetMapping("/{id}")
    public EventFullDto getEventById(@PathVariable Long id,
                                     HttpServletRequest request) {
        log.info("Public: События (Получение подробной информации об опубикованном событии по его идентификатору № {})", id);
     //   stats.newHit("ewm-main-service",request.getRequestURI(),request.getRemoteAddr(),LocalDateTime.now());
        statisticPort.newHit(request.getRequestURI(),request.getRemoteAddr());
        EventFullDto eventFullDto = eventService.getEventById(id);
        log.info("Public: информация о событии № {} получена", id);
        return eventFullDto;
    }


}
