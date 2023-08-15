package ru.practicum.comment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Setter
@Getter
public class NewCommentDto {

    @NotBlank
    @Size(min = 1, max = 7000)
    private String text;
}
