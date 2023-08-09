package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.request.model.EventRequest;
import ru.practicum.request.model.Status;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<EventRequest,Long> {

    List<EventRequest> findByRequesterId(Long requesterId);

    List<EventRequest> findAllByEventId(Long eventId);

    Optional<EventRequest> findByRequesterIdAndId(Long requesterId, Long id);

    Optional<EventRequest> findByRequesterIdAndEventId(Long requesterId, Long eventId);

    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query(value = "UPDATE PUBLIC.Event_Requests " +
            "SET status = :status " +
            "WHERE request_id = :requestId AND requester_id = :userId", nativeQuery = true)
    Optional<EventRequest> changeStatusRequest(@Param("userId") Long userId,
                                          @Param("requestId") Long requestId,
                                          @Param("status") String status
    );

    List<EventRequest> findAllByEventIdAndIdIn(Long eventId, List<Long> id);

    List<EventRequest> findAllByEventIdAndStatus(Long eventId, Status status);

    List<EventRequest> findAllByEventIdAndIdInAndStatus(Long eventId, List<Long> id, Status status);

}