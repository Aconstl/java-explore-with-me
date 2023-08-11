package ru.practicum.port;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.client.StatsClient;
import ru.practicum.model.StatDtoOut;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RequiredArgsConstructor
@Component
public class StatisticPort {

    private final StatsClient statsClient;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public int getAmountOfViews(LocalDateTime eventPublishedOn, String[] uri) {
        List<StatDtoOut> views =  statsClient.getHits(
                        eventPublishedOn != null ? eventPublishedOn.format(formatter) : LocalDateTime.now().format(formatter), //?
                        LocalDateTime.now().format(formatter),
                        uri,
                        true
                        );
        return views != null ? views.size() : 0;
    }
/*
    public Map<Long, Long> getMapOfViews(LocalDateTime eventPublishedOn, String[] uri) {
        List<StatDtoOut> stats = statsClient.getHits(eventPublishedOn, LocalDateTime.now(), uri, true);
      Map<Long,Long> countViewsToId = new HashMap<>();
      for (StatDtoOut s : stats) {
          String viewStatsUri = s.getUri();
          Long id = extractIdFromUri(viewStatsUri);
          countViewsToId.put(id,countViewsToId.getOrDefault(id, 0L) + 1L);
      }
      return countViewsToId;

    }

    private Long extractIdFromUri(String uri) {
        int lastSlashIndex = uri.lastIndexOf('/');
        if (lastSlashIndex != -1 && lastSlashIndex < uri.length() - 1) {
            String idString = uri.substring(lastSlashIndex + 1);
            try {
                return Long.parseLong(idString);
            } catch (BadRequestException e) {
                throw new BadRequestException("Ошибка извлечения id из uri");
            }
        }
        return -1L;
    }
*/
}
