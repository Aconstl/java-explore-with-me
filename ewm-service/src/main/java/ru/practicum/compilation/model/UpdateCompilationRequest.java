package ru.practicum.compilation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class UpdateCompilationRequest {

    @UniqueElements
    private List<Long> events;

    private Boolean pinned;

    @Size(min = 1, max = 50)
    private String title;
}
