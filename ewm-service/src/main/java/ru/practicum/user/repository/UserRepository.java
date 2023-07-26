package ru.practicum.user.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.user.model.User;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where ?1 is null or u.id in ?1")
    List<User> findByIdIn(List<Long> ids, Pageable pageable);


}