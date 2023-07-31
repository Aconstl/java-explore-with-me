package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.Pagination;
import ru.practicum.event.model.*;

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

        return EventMapper.toListShortDto(events);
    }

    public EventFullDto getEventById(Long id) {
        if (id != null) {
            Event event = eventRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("События с указаным id не существует"));
            if (event.getState() != State.PUBLISHED) {
                throw new IllegalArgumentException("Событие еще не опубликовано");
            }
            return EventMapper.toFullDto(event);
        } else {
            throw new NullPointerException("id категории указан неккоректно");
        }
    }
}
