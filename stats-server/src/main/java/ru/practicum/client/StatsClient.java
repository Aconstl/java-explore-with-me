package ru.practicum.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.model.StatDtoOut;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class StatsClient extends Client {

    private static final String API_PREFIX = "/stats";

    @Autowired
    public StatsClient(@Value("${stats-server.url}") String clientUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(clientUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public List<StatDtoOut> getHits(LocalDateTime start, LocalDateTime end, String[] uris, Boolean unique) {
        Map<String,Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique",unique
        );
        String path = API_PREFIX + "?start={start}&end={end}&uris={uris}&unique={unique}";
        ResponseEntity<List<StatDtoOut>> serverResponse = rest.exchange(path, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {}, parameters);
        return serverResponse.getBody();
       // return get("",parameters);
    }
}
