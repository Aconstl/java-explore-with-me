package ru.practicum.event.model;

import lombok.experimental.UtilityClass;
import ru.practicum.port.StatisticPort;
import ru.practicum.category.model.CategoryMapper;
import ru.practicum.event.model.dto.EventFullDto;
import ru.practicum.event.model.dto.EventShortDto;
import ru.practicum.request.model.Status;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.UserMapper;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class EventMapper {

    public static EventShortDto toShortDto(Event event, RequestRepository requestRepository, StatisticPort stats) {
        return EventShortDto.builder()
                .id(event.getId())
                .initiator(UserMapper.toShortDto(event.getInitiator()))
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toDto(event.getCategory()))
                .eventDate(event.getEventDate())
                .confirmedRequests((long) requestRepository.findAllByEventIdAndStatus(event.getId(), Status.CONFIRMED).size())
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(stats.getAmountOfViews(event.getPublishedOn(),
                        new String[]{String.format("/events/%d",event.getId())}))
               // .views(0) //ВРЕМЕННО
                .build();
    }

    public static List<EventShortDto> toListShortDto(List<Event> events,RequestRepository requestRepository,StatisticPort stats) {
        List<EventShortDto> eventsShort = new ArrayList<>();
        for (Event e : events) {
            eventsShort.add(toShortDto(e,requestRepository, stats));
        }
        return eventsShort;
    }

    public static EventFullDto toFullDto(Event event, RequestRepository requestRepository,StatisticPort stats) {
        return EventFullDto.builder()
                .id(event.getId())
                .initiator(UserMapper.toShortDto(event.getInitiator()))
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toDto(event.getCategory()))
                .description(event.getDescription())
                .createdOn(event.getCreatedOn())
                .eventDate(event.getEventDate())
                .location(event.getLocation())
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
                .confirmedRequests((long) requestRepository.findAllByEventIdAndStatus(event.getId(), Status.CONFIRMED).size())
                .publishedOn(event.getPublishedOn() != null ? event.getPublishedOn() : null)
                .views(stats.getAmountOfViews(event.getPublishedOn(),
                        new String[]{String.format("/events/%d",event.getId())}))
               // .views(0) //ВРЕМЕННО
                .build();
    }

    public static List<EventFullDto> toListFulDto(List<Event> events,RequestRepository requestRepository,StatisticPort stats) {
        List<EventFullDto> eventsFull = new ArrayList<>();
        for (Event e : events) {
            eventsFull.add(toFullDto(e,requestRepository, stats));
        }
        return eventsFull;
    }

}
