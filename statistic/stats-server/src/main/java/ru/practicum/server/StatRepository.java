package ru.practicum.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.Stat;
import ru.practicum.model.StatDtoOut;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<Stat,Long> {

    @Query("SELECT new ru.practicum.model.StatDtoOut(s.app, s.uri,  count(distinct s.ip))" +
            "FROM Stat s " +
            "WHERE s.timestamp > ?1 and s.timestamp < ?2 " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY count(distinct s.ip) DESC ")
    List<StatDtoOut> getStatsUniqWithoutUri(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.model.StatDtoOut(s.app, s.uri,  count(s.id)) " +
            "FROM Stat s " +
            "where s.timestamp > ?1 and s.timestamp < ?2 " +
            "GROUP BY s.app, s.uri " +
            "order by count(s.id) desc")
    List<StatDtoOut> getStatsNoUniqWithoutUri(LocalDateTime start, LocalDateTime end);

    @Query("SELECT new ru.practicum.model.StatDtoOut(s.app, s.uri,  count(distinct s.ip)) " +
            "FROM Stat s where s.uri in ?3  " +
            "and  s.timestamp > ?1 and s.timestamp < ?2 " +
            "GROUP BY s.app, s.uri " +
            "order by count(distinct s.ip) desc")
    List<StatDtoOut> getStatsUniqWithUri(LocalDateTime start, LocalDateTime end, String[] uris);

    @Query("SELECT new ru.practicum.model.StatDtoOut(s.app, s.uri,  count(s.id)) " +
            "FROM Stat s where s.uri in ?3  " +
            "and  s.timestamp > ?1 and s.timestamp < ?2 " +
            "GROUP BY s.app, s.uri " +
            "order by count(s) desc ")
    List<StatDtoOut> getStatsNoUniqWithUri(LocalDateTime start, LocalDateTime end, String[] uris);

}
