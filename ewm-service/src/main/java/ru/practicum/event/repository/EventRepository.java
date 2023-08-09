package ru.practicum.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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
            "AND (e.eventDate >= :rangeStart) " +
            "AND (e.eventDate <= :rangeEnd) " +
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
            "AND (e.eventDate >= :rangeStart) " +
            "AND (e.eventDate <= :rangeEnd) " +
            "AND (e.state = ru.practicum.event.model.State.PUBLISHED) "
                 )
    List<Event> getEventsByParam(@Param("text") String text,
                                 @Param("categories") Set<Long> categories,
                                 @Nullable @Param("paid") Boolean paid,
                                 @Param("rangeStart") LocalDateTime rangeStart,
                                 @Param("rangeEnd") LocalDateTime rangeEnd,
                                 Pageable pageable);

    List<Event> findByCategoryId(Long categoryId);

    List<Event> findByInitiatorId(Long initiatorId, Pageable pageable);

    Optional<Event> findByInitiatorIdAndId(Long initiatorId, Long id);

    @Query("SELECT e " +
            "FROM Event e " +
        //    "RIGHT JOIN  EventRequest r on e.id = r.event.id " +
            "WHERE (:users IS NULL OR e.initiator.id in :users)  " +
            "AND (:states IS NULL OR e.state in :states) " +
            "AND (:categories IS NULL OR e.category.id in :categories) " +
            "AND (e.eventDate >= :rangeStart) " +
            "AND (e.eventDate <= :rangeEnd) "// +
        //    "GROUP BY e "// +
         //   "HAVING count(r) < e.participantLimit"
    )
    List<Event> getEventFilterForAdmin(@Param("users") Set<Long> users,
                                       @Param("states") Set<State> states,
                                       @Param("categories") Set<Long> categories,
                                       @Param("rangeStart") LocalDateTime rangeStart,
                                       @Param("rangeEnd") LocalDateTime rangeEnd,
                                          Pageable pageable);

    @Query(value = "select e.event_date " +
            "from events e " +
            "order by e.event_date asc " +
            "limit 1", nativeQuery = true)
    LocalDateTime getOldEventTime();

    @Query(value = "select e.event_date " +
            "from events e " +
            "order by e.event_date desc " +
            "limit 1", nativeQuery = true)
    LocalDateTime getNewEventTime();

    List<Event> findAllByIdIn(List<Long> events);
}
