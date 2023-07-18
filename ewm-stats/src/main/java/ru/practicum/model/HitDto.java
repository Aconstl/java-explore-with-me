package ru.practicum.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor (force = true)
public class HitDto {
    @NotBlank
    private final String app;
    @NotBlank
    private final String uri;
    @NotBlank
    @Pattern(regexp = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$")
    private final String ip;

    @NotNull
    @JsonFormat(pattern = "YYYY-MM-DD HH:mm:ss")
    private LocalDateTime timestamp;

}
