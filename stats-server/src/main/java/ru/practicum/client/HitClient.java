package ru.practicum.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.model.HitDto;

import java.time.LocalDateTime;


@Service
public class HitClient extends Client {
    private static final String API_PREFIX = "/hit";

    @Autowired
    public HitClient(@Value("${stats-server.url}") String clientUrl, RestTemplateBuilder builder) {
        super (builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(clientUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public ResponseEntity<Object> newHit(String app, String uri, String ip, LocalDateTime timestamp) {
        HitDto hitDto = new HitDto(app,uri,ip,timestamp);
        return post("", hitDto);
    }

}