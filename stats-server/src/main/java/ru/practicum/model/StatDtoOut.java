package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Entity;

@AllArgsConstructor
@Getter
public class StatDtoOut {
    private final String app;
    private final String uri;
    private final Long hits;
}
