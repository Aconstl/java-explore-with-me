package ru.practicum.comment.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.service.CommentService;

@Slf4j
@RestController
@RequestMapping("/admin/comments")
@RequiredArgsConstructor
public class AdminCommentController {

    private final CommentService commentService;

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCommentByAdmin(
            @PathVariable Long commentId
    ) {
        log.info("Admin: комментарии (удаление комментария № {} )", commentId);
        commentService.deleteCommentByAdmin(commentId);
        log.info("Admin: комментарий  № {} удален", commentId);
    }
}
