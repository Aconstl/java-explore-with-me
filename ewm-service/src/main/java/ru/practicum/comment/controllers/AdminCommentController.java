package ru.practicum.comment.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.comment.service.CommentService;

@Slf4j
@RestController
@RequestMapping("/admin/comments")
@RequiredArgsConstructor
public class AdminCommentController {

    private final CommentService commentService;

    @DeleteMapping("/{commentId}")
    public void deleteCommentByAdmin(
            @PathVariable Long commentId
    ) {
        log.info("Admin: комментарии (удаление комментария № {} )", commentId);
        commentService.deleteCommentByAdmin(commentId);
        log.info("Admin: комментарий  № {} удален", commentId);
    }
}
