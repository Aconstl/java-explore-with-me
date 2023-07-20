package ru.practicum.server;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.Stat;
import ru.practicum.model.StatDtoOut;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<Stat,Long> {
    /*
    @Query(value = "select s.app, s.uri, count(distinct s.ip) " +
            "from statistic s " +
            "where s.timestamp > :start and s.timestamp < :end " +
            "group by s.app, s.uri " +
            "order by count(distinct s.ip) desc", nativeQuery = true)
    List<StatDtoOut> getStatsUniqWithoutUri(@Param("start") LocalDateTime start,
                                              @Param("end") LocalDateTime end);

    */

    @Query("SELECT new ru.practicum.model.StatDtoOut(s.app, s.uri,  count(distinct s.ip))" +
            "FROM Stat s " +
            "WHERE s.timestamp > ?1 and s.timestamp < ?2 " +
            "GROUP BY s.app, s.uri " +
            "ORDER BY count(distinct s.ip) DESC ")
    List<StatDtoOut> getStatsUniqWithoutUri(LocalDateTime start, LocalDateTime end);

    /*
    @Query(value = "select s.app, s.uri, count(s.stat_id) " +
            "from statistic s " +
            "where s.timestamp > :start and s.timestamp < :end " +
            "group by s.app, s.uri " +
            "order by count(s.stat_id) desc", nativeQuery = true)
    List<StatDtoOut> getStatsNoUniqWithoutUri(@Param("start") LocalDateTime start,
                                              @Param("end") LocalDateTime end);
    */


    @Query("SELECT new ru.practicum.model.StatDtoOut(s.app, s.uri,  count(s.id)) " +
            "FROM Stat s " +
            "where s.timestamp > ?1 and s.timestamp < ?2 " +
            "GROUP BY s.app, s.uri " +
            "order by count(s.id) desc")
    List<StatDtoOut> getStatsNoUniqWithoutUri(LocalDateTime start, LocalDateTime end);

    /*
    @Query(value = "select s.app, s.uri, count(s.stat_id) " +
            "from statistic s " +
            "where s.uri in :uri " +
            "and s.timestamp > :start and s.timestamp < :end " +
            "group by s.app, s.uri " +
            "order by count(s.stat_id) desc", nativeQuery = true)
    List<StatDtoOut> getStatsUniqWithUri(@Param("uri") String[] uri,
                                           @Param("start") LocalDateTime start,
                                           @Param("end") LocalDateTime end);
    */

    @Query("SELECT new ru.practicum.model.StatDtoOut(s.app, s.uri,  count(distinct s.ip)) " +
            "FROM Stat s where s.uri in ?3  " +
            "and  s.timestamp > ?1 and s.timestamp < ?2 " +
            "GROUP BY s.app, s.uri " +
            "order by count(distinct s.ip) desc")
    List<StatDtoOut> getStatsUniqWithUri(LocalDateTime start, LocalDateTime end, String[] uris);

    /*
    @Query(value = "select s.app, s.uri, count(distinct s.ip) " +
            "from statistic s " +
            "where s.uri in :uri " +
            "and s.timestamp > :start and s.timestamp < :end " +
            "group by s.app, s.uri " +
            "order by count(distinct s.ip) desc", nativeQuery = true)
    List<StatDtoOut> getStatsNoUniqWithUri(@Param("uri") String[] uri,
                                           @Param("start") LocalDateTime start,
                                           @Param("end") LocalDateTime end);
    */

    @Query("SELECT new ru.practicum.model.StatDtoOut(s.app, s.uri,  count(s.id)) " +
            "FROM Stat s where s.uri in ?3  " +
            "and  s.timestamp > ?1 and s.timestamp < ?2 " +
            "GROUP BY s.app, s.uri " +
            "order by count(s) desc ")
    List<StatDtoOut> getStatsNoUniqWithUri(LocalDateTime start, LocalDateTime end, String[] uris);

}
