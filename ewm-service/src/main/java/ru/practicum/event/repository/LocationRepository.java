package ru.practicum.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.event.model.Location;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location,Long> {

    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query(value = "UPDATE PUBLIC.locations " +
            "SET lat = :lat, " +
            "lon = :lon, " +
            "WHERE location_id = :locationId", nativeQuery = true)
    Optional<Location> updateEvent(@Param("locationId") Long locationId,
                                @Param("lat") float lat,
                                @Param("lon") float lon
    );

}
