package ru.practicum.comment.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class CommentListDto {

    private Long eventId;

    private String titleEvent;

    private List<CommentShortDto> comments;

}
