package ru.practicum.server;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.practicum.model.HitDto;
import ru.practicum.model.Stat;
import ru.practicum.model.StatDtoOut;
import ru.practicum.model.StatMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class StatService {

    private final StatRepository repository;

    public void newHit(HitDto hitDto) {
        Stat stat = StatMapper.fromDto(hitDto);
        repository.save(stat);
    }

    @Transactional(readOnly = true)
    public List<StatDtoOut> getStats(LocalDateTime start,LocalDateTime end,String[] uris, boolean unique) {
       if (uris == null || uris.length == 0) {
           if (unique) {
               return repository.getStatsUniqWithoutUri(start,end);
           } else {
               return repository.getStatsNoUniqWithoutUri(start,end);
           }
       } else {
           if (unique) {
               return repository.getStatsUniqWithUri(start,end,uris);
           } else {
               return repository.getStatsNoUniqWithUri(start,end,uris);
           }
       }
    }
}
