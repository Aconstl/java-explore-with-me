package ru.practicum.server;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.Stat;

public interface StatRepository extends JpaRepository<Stat,Long> {
}
