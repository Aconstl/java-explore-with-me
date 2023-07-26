package ru.practicum.user.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDto {

    private String email;

    private Long id;

    private String name;
}
