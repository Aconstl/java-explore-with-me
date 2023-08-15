package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comment.model.Comment;
import ru.practicum.comment.model.CommentFullDto;
import ru.practicum.comment.model.CommentMapper;
import ru.practicum.comment.model.NewCommentDto;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.customException.model.NotFoundException;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    public CommentFullDto createComment(Long eventId, Long userId, NewCommentDto newComment) {
        User commentator = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указаным id не найден"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с указаным id не найдено"));

        Comment comment = new Comment();
            comment.setCommentator(commentator);
            comment.setEvent(event);
            comment.setText(newComment.getText());
            comment.setCreated(LocalDateTime.now());

        comment = commentRepository.save(comment);
        return CommentMapper.toDto(comment);
    }

    public CommentFullDto updateComment(Long userId, Long commentId, NewCommentDto updateComment) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указаным id не найден"));
        Comment comment = commentRepository.findByCommentatorIdAndId(userId,commentId)
                .orElseThrow(() -> new NotFoundException("комментарий от данного пользователя с указаным id не найден"));

        comment.setText(updateComment.getText());

        comment = commentRepository.saveAndFlush(comment);

        return CommentMapper.toDto(comment);
    }

    public void deleteComment(Long userId, Long commentId) {
        commentRepository.findByCommentatorIdAndId(userId,commentId)
                .ifPresentOrElse(commentRepository::delete, () -> {
                throw new NotFoundException("комментарий от данного пользователя с указаным id не найден");
                });
    }
}
