package ru.practicum.category.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class NewCategoryDto {

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

}
