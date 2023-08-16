package ru.practicum.comment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.comment.model.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    Optional<Comment> findByCommentatorIdAndId(Long commentatorId, Long id);

    List<Comment> findAllByCommentatorId(Long commentatorId);

    List<Comment> findAllByEventId(Long eventId, Pageable pageable);
}
