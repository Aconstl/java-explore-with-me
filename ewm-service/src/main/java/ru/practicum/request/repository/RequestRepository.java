package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.request.model.EventRequest;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<EventRequest,Long> {

    List<EventRequest> findByRequesterId(Long requesterId);

    List<EventRequest> findByEventId(Long eventId);

    Optional<EventRequest> findByRequesterIdAndId(Long requesterId, Long id);

    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query(value = "UPDATE PUBLIC.REQUESTS " +
            "SET status = :status " +
            "WHERE request_id = :requestId AND requester_id = :userId", nativeQuery = true)
    Optional<EventRequest> changeStatusRequest(@Param("userId") Long userId,
                                          @Param("requestId") Long requestId,
                                          @Param("status") String status
    );


}