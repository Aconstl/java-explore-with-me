package ru.practicum.comment.model;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

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

    public static List<CommentFullDto> toListDto(List<Comment> comments) {
        List<CommentFullDto> listDto = new ArrayList<>();
        for (Comment c: comments) {
            listDto.add(toDto(c));
        }
        return listDto;
    }

    public static CommentShortDto toShortDto(Comment comment) {
        return CommentShortDto.builder()
                .commentatorName(comment.getCommentator().getName() + " (" + comment.getCommentator().getEmail() + ")")
                .text(comment.getText())
                .created(comment.getCreated())
                .build();
    }

    public static List<CommentShortDto> toListShortDto(List<Comment> comments) {
        List<CommentShortDto> listDto = new ArrayList<>();
        for (Comment c: comments) {
            listDto.add(toShortDto(c));
        }
        return listDto;
    }
}
