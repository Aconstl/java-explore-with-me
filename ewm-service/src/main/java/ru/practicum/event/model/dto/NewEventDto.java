package ru.practicum.event.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.event.model.Location;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class NewEventDto {
    @NotBlank
    @Size(min = 20,max = 2000)
    private String annotation;
    @NotNull
    private Long category;
    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Future
    private LocalDateTime eventDate;
    @NotNull
    private Location location;
    private Boolean paid = false;
    @PositiveOrZero
    private Long participantLimit = 0L;
    private Boolean requestModeration = true;
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
}
