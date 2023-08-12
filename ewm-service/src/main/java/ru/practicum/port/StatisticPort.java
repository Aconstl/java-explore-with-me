package ru.practicum.port;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
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

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public int getAmountOfViews(Event event, String[] uri) {
        //проверить данный вариант
            List<StatDtoOut> views = statsClient.getHits(
                    event.getPublishedOn() != null ? event.getPublishedOn().format(formatter) : event.getCreatedOn().format(formatter), //?
                    LocalDateTime.now().format(formatter),
                    uri,
                    true
            );
            return views != null ? views.size() : 0;
    }
}
