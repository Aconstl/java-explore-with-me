package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.Pagination;
import ru.practicum.comment.model.*;
import ru.practicum.comment.repository.CommentRepository;
import ru.practicum.customException.model.ConflictException;
import ru.practicum.customException.model.NotFoundException;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

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

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConflictException("Данное событие еще не опубликовано");
        }

        Comment comment = new Comment();
            comment.setCommentator(commentator);
            comment.setEvent(event);
            comment.setText(newComment.getText());
            comment.setCreated(LocalDateTime.now());

        comment = commentRepository.save(comment);
        return CommentMapper.toDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentFullDto> getCommentsByUser(Long userId, Long from, Long size) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указаным id не найден"));
        Pageable pageable = Pagination.setPageable(from,size);
        List<Comment> comments = commentRepository.findAllByCommentatorIdOrderByIdDesc(userId, pageable);
        return CommentMapper.toListDto(comments);
    }

    @Transactional(readOnly = true)
    public CommentFullDto getComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("комментарий с указаным id не найден"));
        if (!comment.getCommentator().getId().equals(userId)) {
            throw new ConflictException("комментарий принадлежит не данному пользователю");
        }
        return CommentMapper.toDto(comment);
    }

    @Transactional(readOnly = true)
    public CommentListDto getCommentsEvent(Long eventId, Long from, Long size) {
        Pageable pageable = Pagination.setPageable(from,size);
        Event event = eventRepository.findById(eventId)
                //Не найден - 404
                .orElseThrow(() -> new NotFoundException("События с указаным id не существует"));
        if (event.getState() != State.PUBLISHED) {
            //Недоступнос - 404
            throw new NotFoundException("Событие еще не опубликовано");
        }
        List<Comment> comments = commentRepository.findAllByEventIdOrderByIdDesc(eventId,pageable);
        List<CommentShortDto> shortComments = CommentMapper.toListShortDto(comments);
        return new CommentListDto(eventId, event.getTitle(), shortComments);
    }

    public CommentFullDto updateComment(Long userId, Long commentId, NewCommentDto updateComment) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указаным id не найден"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("комментарий с указаным id не найден"));
        if (!comment.getCommentator().getId().equals(userId)) {
            throw new ConflictException("комментарий принадлежит не данному пользователю");
        }

        comment.setText(updateComment.getText());

        comment = commentRepository.saveAndFlush(comment);

        return CommentMapper.toDto(comment);
    }

    public void deleteComment(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("комментарий с указаным id не найден"));
        if (!comment.getCommentator().getId().equals(userId)) {
            throw new ConflictException("комментарий принадлежит не данному пользователю");
        }
        commentRepository.delete(comment);
    }

    public void deleteCommentByAdmin(Long commentId) {
        commentRepository.findById(commentId)
                .ifPresentOrElse(commentRepository::delete, () -> {
                    throw new NotFoundException("комментарий не найден");
                });
    }
}
