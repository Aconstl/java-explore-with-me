package ru.practicum.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.request.model.EventRequest;

public interface RequestRepository extends JpaRepository<EventRequest,Long> {
}
