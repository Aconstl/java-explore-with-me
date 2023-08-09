package ru.practicum.event.model;

import lombok.experimental.UtilityClass;
import ru.practicum.StatisticPort;
import ru.practicum.category.model.CategoryMapper;
import ru.practicum.event.model.dto.EventFullDto;
import ru.practicum.event.model.dto.EventShortDto;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.UserMapper;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class EventMapper {

    public static EventShortDto toShortDto(Event event, RequestRepository requestRepository, StatisticPort stats) {
        return EventShortDto.builder()
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toDto(event.getCategory()))
                .confirmedRequests((long) requestRepository.findAllByEventId(event.getId()).size())
                .eventDate(event.getEventDate().toString())
                .id(event.getId())
                .initiator(UserMapper.toShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .title(event.getTitle())
              //  .views(stats.getAmountOfViews(event.getPublishedOn(),
               //         new String[]{String.format("/events/%d",event.getId())}))
                .views(0) //ВРЕМЕННО
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
                .annotation(event.getAnnotation())
                .category(CategoryMapper.toDto(event.getCategory()))
                .confirmedRequests((long) requestRepository.findAllByEventId(event.getId()).size())
                .createdOn(event.getCreatedOn().toString())
                .description(event.getDescription())
                 .eventDate(event.getEventDate().toString())
                .id(event.getId())
                .initiator(UserMapper.toShortDto(event.getInitiator()))
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn() != null ? event.getPublishedOn().toString() : null)
                .requestModeration(event.getRequestModeration())
                .state(event.getState())
                .title(event.getTitle())
              //  .views(stats.getAmountOfViews(event.getPublishedOn(),
              //          new String[]{String.format("/events/%d",event.getId())}))
                .views(0) //ВРЕМЕННО
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
