package ru.practicum.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.model.HitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Service
public class HitClient extends Client {
    private static final String API_PREFIX = "/hit";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public HitClient(@Value("${stats-server.url}") String clientUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(clientUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public void newHit(String app, String uri, String ip, LocalDateTime timestamp) {
        HitDto hitDto = new HitDto(app,uri,ip,timestamp.format(formatter));
        post("", hitDto);
    }

}