package ru.practicum.comment.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.model.CommentFullDto;
import ru.practicum.comment.model.NewCommentDto;
import ru.practicum.comment.service.CommentService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users/{userId}/comments")
@RequiredArgsConstructor
@Validated
public class PrivateCommentController {

    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentFullDto createComment(
            @PathVariable Long userId,
            @RequestParam Long eventId,
            @RequestBody @Valid NewCommentDto newComment
            ) {
        log.info("Private: комментарии (добавление нового комментария пользователем с id {} для события № {})", userId, eventId);
        CommentFullDto commentFullDto = commentService.createComment(eventId, userId, newComment);
        log.info("Private: комментарий добавлен пользователем с id {} для события № {} успешно", userId, eventId);
        return commentFullDto;
    }

    @GetMapping
    public List<CommentFullDto> getCommentsByUser(@PathVariable Long userId) {
        log.info("Private: комментарии (получение всех комментариев пользователя с id {} )", userId);
        return commentService.getCommentsByUser(userId);
    }

    @GetMapping("/{commentId}")
    public CommentFullDto getComment(@PathVariable Long userId,
                                           @PathVariable Long commentId) {
        log.info("Private: комментарии (получение комментария под id {} пользователя с id {} )", commentId, userId);
        return commentService.getComment(userId,commentId);
    }

    @PatchMapping("/{commentId}")
    public CommentFullDto updateComment(
            @PathVariable Long userId,
            @PathVariable Long commentId,
            @RequestBody @Valid NewCommentDto newComment
    ) {
        log.info("Private: комментарии (обновление комментария № {} пользователем с id {})", commentId, userId);
        CommentFullDto commentFullDto = commentService.updateComment(userId,commentId, newComment);
        log.info("Private: комментарий  № {} обновлен успешно пользователем с id {}", commentId, userId);
        return commentFullDto;
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(
            @PathVariable Long userId,
            @PathVariable Long commentId
    ) {
        log.info("Private: комментарии (обновление комментария № {} пользователем с id {})", commentId, userId);
        commentService.deleteComment(userId, commentId);
        log.info("Private: комментарий  № {} обновлен успешно пользователем с id {}", commentId, userId);
    }

}
