package ru.practicum.event.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.category.model.CategoryDto;
import ru.practicum.user.model.UserShortDto;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class EventShortDto {

    private Long id;
    private UserShortDto initiator;
    private String annotation;
    private CategoryDto category;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String eventDate;
    private Long confirmedRequests;
    private Boolean paid;
    private String title;
    private Integer views;

}
