package ru.practicum.server;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.model.HitDto;
import ru.practicum.model.StatDtoOut;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class StatController {

    private final StatService service;

    @PostMapping("/hit")
    public ResponseEntity<Object> newHit(
            @RequestBody @Valid HitDto hitDto
            ) {
        log.debug("сохранение информации о запросе");
        service.newHit(hitDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/stats")
    public List<StatDtoOut> getStats(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end,
            @RequestParam(required = false) String[] uris,
            @RequestParam(defaultValue = "false") boolean unique
            ) {
        log.debug("получение статистики по посещениям");
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Ошибка валидации: конец выборки назначен раньше начала");
        }
        return service.getStats(start,end,uris,unique);
    }
}
