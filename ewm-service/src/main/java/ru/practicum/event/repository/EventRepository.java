package ru.practicum.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event,Long> {
    @Query("SELECT e " +
            "FROM Event e " +
            "RIGHT JOIN  EventRequest r on e.id = r.event.id " +
            "WHERE (:text IS NULL OR e.description like %:text% OR e.annotation like %:text%)  " +
            "AND (:categories IS NULL OR e.category.id in :categories) " +
            "AND (:paid IS NULL OR e.paid = :paid) " +
            "AND (:rangeStart IS NULL OR e.eventDate >= :rangeStart) " +
            "AND (:rangeEnd IS NULL OR e.eventDate <= :rangeEnd) " +
            "AND (r.status = ru.practicum.request.model.Status.CONFIRMED OR r.status = null) " +
            "AND (e.state = ru.practicum.event.model.State.PUBLISHED) " +
            "GROUP BY e " +
            "HAVING count(r) < e.participantLimit"
    )
    List<Event> getAvailableEventsByParam(@Param("text") String text,
                                          @Param("categories") Set<Long> categories,
                                          @Param("paid") Boolean paid,
                                          @Param("rangeStart") LocalDateTime rangeStart,
                                          @Param("rangeEnd") LocalDateTime rangeEnd,
                                          Pageable pageable);

    @Query("SELECT e " +
            "FROM Event e " +
            "WHERE (:text IS NULL OR e.description like %:text% OR e.annotation like %:text%)  " +
            "AND (:categories IS NULL OR e.category.id in :categories) " +
            "AND (:paid IS NULL OR e.paid = :paid) " +
            "AND (:rangeStart IS NULL OR e.eventDate >= :rangeStart) " +
            "AND (e.state = ru.practicum.event.model.State.PUBLISHED) " +
            "AND (:rangeEnd IS NULL OR e.eventDate <= :rangeEnd)")
    List<Event> getEventsByParam(@Param("text") String text,
                                 @Param("categories") Set<Long> categories,
                                 @Nullable @Param("paid") Boolean paid,
                                 @Param("rangeStart") LocalDateTime rangeStart,
                                 @Param("rangeEnd") LocalDateTime rangeEnd,
                                 Pageable pageable);

    List<Event> findByCategoryId(Long categoryId);

    List<Event> findByInitiatorId(Long initiatorId, Pageable pageable);

    Optional<Event> findByInitiatorIdAndId(Long initiatorId, Long id);

    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query(value = "UPDATE PUBLIC.Events " +
            "SET annotation = :annotation, " +
            "category_id = :category, " +
            "description = :description, " +
            "event_date = :eventDate, " +
            "paid = :paid, " +
            "participant_limit = :participantLimit, " +
            "request_moderation = :requestModeration, " +
            "title = :title, " +
            "state = :state " +
            "WHERE event_id = :eventId", nativeQuery = true)
    Optional<Event> updateEvent(@Param("eventId") Long eventId,
                                @Param("category") Long categoryId,
                                @Param("description") String description,
                                @Param("eventDate") LocalDateTime eventDate,
                                @Param("paid") Boolean paid,
                                @Param("participantLimit") Long participantLimit,
                                @Param("requestModeration") Boolean requestModeration,
                                @Param("title") String title,
                                @Param("state") State state
        );
}