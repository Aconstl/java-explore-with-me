package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.Pagination;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.customException.model.BadRequestException;
import ru.practicum.customException.model.ConflictException;
import ru.practicum.customException.model.NotFoundException;
import ru.practicum.event.model.*;

import org.springframework.data.domain.Pageable;
import ru.practicum.event.model.dto.EventFullDto;
import ru.practicum.event.model.dto.EventShortDto;
import ru.practicum.event.model.dto.NewEventDto;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.request.model.dto.ParticipationRequestDto;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final RequestRepository requestRepository;

    //PUBLIC
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

        return EventMapper.toListShortDto(events,requestRepository);
    }

    public EventFullDto getEventById(Long id) {
        if (id != null) {
            Event event = eventRepository.findById(id)
                    //Не найден - 404
                    .orElseThrow(() -> new NotFoundException("События с указаным id не существует"));
            if (event.getState() != State.PUBLISHED) {
                //Недоступнос - 404
                throw new NotFoundException("Событие еще не опубликовано");
            }
            return EventMapper.toFullDto(event,requestRepository);
        } else {
            //Не корректно - 400
            throw new BadRequestException("id категории указан неккоректно");
        }
    }

    //PRIVATE
    public List<EventShortDto> getUserEvents(Long id, Long from, Long size) {
        Pageable pageable = Pagination.setPageable(from,size);
        List<Event> eventList = eventRepository.findByInitiatorId(id,pageable);
        return EventMapper.toListShortDto(eventList,requestRepository);
    }

    public EventFullDto createEvent(Long userId, NewEventDto newEvent) {
        LocalDateTime actualPublicationTime = LocalDateTime.now().plusHours(2);
        if (newEvent.getEventDate().isBefore(actualPublicationTime)) {
            //Событие не удовлетворяет правилам создания  - 409
            throw new ConflictException("Время мероприятия должно быть минимум через 2 часа");
        }
        User initiator = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указаным id не найден"));

        Category category = categoryRepository.findById(newEvent.getCategory())
                //Если не найден с данным id - 404
                .orElseThrow(() -> new NotFoundException("Категории с указаным id не существует"));

        Event event = new Event();
            event.setInitiator(initiator);
            event.setAnnotation(newEvent.getAnnotation());
            event.setCategory(category);
            event.setDescription(newEvent.getDescription());
            event.setCreatedOn(LocalDateTime.now());
            event.setEventDate(newEvent.getEventDate());
            event.setLocation(newEvent.getLocation());
            event.setPaid(newEvent.getPaid());
            event.setParticipantLimit(newEvent.getParticipantLimit() != null ? newEvent.getParticipantLimit() : 0L);
            event.setRequestModeration(newEvent.getRequestModeration() != null ? newEvent.getRequestModeration() : true);
            event.setTitle(newEvent.getTitle());
            event.setState(State.PENDING);
            event.setViews(0L);
            event.setPublishedOn(null);

         event = eventRepository.save(event);
        return EventMapper.toFullDto(event,requestRepository);
    }

    public EventFullDto getUserEvent(Long userId, Long eventId) {
        throw new UnsupportedOperationException("Не реализован");
    }

    public EventFullDto updateEvent(Long userId, Long eventId, NewEventDto updEvent) {
        Event event = eventRepository.findByInitiatorIdAndId(userId,eventId)
                //Не найден - 404
                .orElseThrow(() -> new NotFoundException("События с указаным id не существует"));
        if (updEvent == null) {
            return EventMapper.toFullDto(event, requestRepository);
        }
/*
        if (!event.getAnnotation().equals(updEvent.getAnnotation())) {
            eventRepository.updateAnnotation(eventId,userId, updEvent.getAnnotation());
        }

        if (!event.getCategory().getId().equals(updEvent.getCategory())) {
            eventRepository.updateCategory(eventId,userId, updEvent.getCategory());
        }
*/



        throw new UnsupportedOperationException("Не реализован");
    }

    public List<ParticipationRequestDto> getRequestsInUserEvent(Long userId, Long eventId) {
        throw new UnsupportedOperationException("Не реализован");
    }
}
