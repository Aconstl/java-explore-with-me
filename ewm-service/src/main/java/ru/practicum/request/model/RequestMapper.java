package ru.practicum.request.model;

import lombok.experimental.UtilityClass;
import ru.practicum.request.model.dto.ParticipationRequestDto;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class RequestMapper {

    public static ParticipationRequestDto toDto(EventRequest request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .requester(request.getRequester().getId())
                .event(request.getEvent().getId())
                .created(request.getCreated())
                .status(request.getStatus().toString())
                .build();
    }

    public static List<ParticipationRequestDto> toListDto(List<EventRequest> requests) {
        List<ParticipationRequestDto> listsDto = new ArrayList<>();
        for (EventRequest r : requests) {
            listsDto.add(toDto(r));
        }
        return listsDto;
    }
}
