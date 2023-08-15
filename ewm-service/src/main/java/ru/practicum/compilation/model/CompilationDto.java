package ru.practicum.compilation.model;

import lombok.*;
import ru.practicum.event.model.dto.EventShortDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CompilationDto {

    private Long id;
    private List<EventShortDto> events;

    private Boolean pinned;

    private String title;

}
