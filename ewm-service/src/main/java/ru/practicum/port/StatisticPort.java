package ru.practicum.port;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.practicum.client.HitClient;
import ru.practicum.client.StatsClient;
import ru.practicum.event.model.Event;
import ru.practicum.model.StatDtoOut;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RequiredArgsConstructor
@Component
public class StatisticPort {

    private final StatsClient statsClient;
    private final HitClient hitClient;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Value("${on-static}")
    private Boolean onStatic;

    public int getAmountOfViews(Event event, String[] uri) {
            if (!onStatic) {
                return 0;
            } else {
                List<StatDtoOut> views = statsClient.getHits(
                        event.getPublishedOn() != null ? event.getPublishedOn().format(formatter) : event.getCreatedOn().format(formatter), //?
                        LocalDateTime.now().format(formatter),
                        uri,
                        true
                );
                return views != null ? views.size() : 0;
            }
    }

    public void newHit(String uri, String ip) {
        if (onStatic) {
            hitClient.newHit("ewm-main-service", uri, ip, LocalDateTime.now());
        }
    }
}
