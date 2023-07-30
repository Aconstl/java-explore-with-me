package ru.practicum.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.category.model.Category;

import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query (value = "UPDATE PUBLIC.CATEGORIES " +
            "SET name = :name " +
            "WHERE category_id = :catId", nativeQuery = true)
    Optional<Category> updateCategoryName(@Param("name") String name,
                            @Param("catId") Long catId);
}
