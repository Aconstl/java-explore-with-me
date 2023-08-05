package ru.practicum.request.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;


@Builder
public class ParticipationRequestDto {

    private Long id;

    private Long event;

    private Long requester;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String created;

    private String status;

}