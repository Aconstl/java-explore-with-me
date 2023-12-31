package ru.practicum.category.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CategoryDto {

    private Long id;

    private String name;
}
