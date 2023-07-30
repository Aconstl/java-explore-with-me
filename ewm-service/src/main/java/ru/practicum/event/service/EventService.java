package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.Pagination;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.event.model.EventShortDto;
import ru.practicum.event.model.SortEvent;

import org.springframework.data.domain.Pageable;
import ru.practicum.event.repository.EventRepository;

import java.util.List;
import java.util.Set;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<EventShortDto>  getEventWithFilter(String text,
                                                   Set<Long> categories,
                                                   Boolean paid,
                                                   LocalDateTime rangeStart,
                                                   LocalDateTime rangeEnd,
                                                   Boolean onlyAvailable,
                                                   SortEvent sort,
                                                   Long from,
                                                   Long size) {
        Pageable pageable;
        if (sort == SortEvent.EVENT_DATE) {
            pageable = Pagination.setPageable(from,size,"eventDate");
        } else {
            pageable = Pagination.setPageable(from,size);
        }

        List<Event> events;
        if (onlyAvailable) {
            events = eventRepository.getAvailableEventsByParam(text, categories, paid, rangeStart, rangeEnd, pageable);
        } else {
            events = eventRepository.getEventsByParam(text, categories, paid, rangeStart, rangeEnd, pageable);
        }

        return events
    }

    public EventFullDto getEventById(Long id) {
        throw new UnsupportedOperationException("Не реализоан");
    }
}
