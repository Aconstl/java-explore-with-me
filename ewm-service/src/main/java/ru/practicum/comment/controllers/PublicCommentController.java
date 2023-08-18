package ru.practicum.comment.controllers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.model.CommentListDto;
import ru.practicum.comment.service.CommentService;

@Slf4j
@RestController
@RequestMapping("/events/{eventId}/comments")
@RequiredArgsConstructor
public class PublicCommentController {

    private final CommentService commentService;

    @GetMapping
    public CommentListDto getCommentsEvent(@PathVariable Long eventId,
                                           @RequestParam(defaultValue = "0") Long from,
                                           @RequestParam(defaultValue = "10") Long size) {
        log.info("Public: комментарии (получение комментариев события № {})", eventId);

        return commentService.getCommentsEvent(eventId, from, size);
    }

}
