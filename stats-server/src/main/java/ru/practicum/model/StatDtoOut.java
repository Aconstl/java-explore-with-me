package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StatDtoOut {
    private final String app;
    private final String uri;
    private final Long hits;
}
