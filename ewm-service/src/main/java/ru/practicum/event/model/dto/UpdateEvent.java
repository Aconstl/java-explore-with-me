package ru.practicum.event.model.dto;

import ru.practicum.event.model.Location;

import java.time.LocalDateTime;

public interface UpdateEvent {

    String getAnnotation();

    Long getCategory();

    String getDescription();

    LocalDateTime getEventDate();

    Location getLocation();

    Boolean getPaid();

    Long getParticipantLimit();

    Boolean getRequestModeration();

    String getTitle();

}
