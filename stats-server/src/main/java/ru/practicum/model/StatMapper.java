package ru.practicum.model;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StatMapper {

    public static Stat fromDto(HitDto hitDto) {
        return Stat.builder()
                .app(hitDto.getApp())
                .uri(hitDto.getUri())
                .ip(hitDto.getIp())
                .timestamp(hitDto.getTimestamp())
                .build();
    }

}
