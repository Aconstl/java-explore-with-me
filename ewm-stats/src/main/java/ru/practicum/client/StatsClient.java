package ru.practicum.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class StatsClient extends Client {

    private static final String API_PREFIX = "/stats";

    @Autowired
    public StatsClient(@Value("${ewm-stats.url}") String clientUrl, RestTemplateBuilder builder) {
        super (builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(clientUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public ResponseEntity<Object> getHits (LocalDateTime start, LocalDateTime end, String[] uri, Boolean unique) {
        Map<String,Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uri", uri,
                "unique",unique
        );
        return get("",parameters);
    }
}
