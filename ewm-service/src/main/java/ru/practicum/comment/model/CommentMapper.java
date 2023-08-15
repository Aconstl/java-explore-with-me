package ru.practicum.comment.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommentMapper {
    public static CommentFullDto toDto(Comment comment) {
        return CommentFullDto.builder()
                .id(comment.getId())
                .commentatorId(comment.getCommentator().getId())
                .eventId(comment.getEvent().getId())
                .text(comment.getText())
                .created(comment.getCreated())
                .build();
    }

}
