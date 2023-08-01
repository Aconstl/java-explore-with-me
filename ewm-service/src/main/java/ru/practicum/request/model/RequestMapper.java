package ru.practicum.request.model;

import lombok.experimental.UtilityClass;
import ru.practicum.request.model.dto.ParticipationRequestDto;

@UtilityClass
public class RequestMapper {

    public static ParticipationRequestDto toDto(EventRequest request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .requester(request.getRequester().getId())
                .event(request.getEvent().getId())
                .created(request.getCreated().toString())
                .status(request.getStatus().toString())
                .build();
    }

}
