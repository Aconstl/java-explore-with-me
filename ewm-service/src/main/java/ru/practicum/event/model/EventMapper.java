package ru.practicum.event.model;


import lombok.experimental.UtilityClass;
import ru.practicum.category.model.CategoryMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.model.UserMapper;

@UtilityClass
public class EventMapper {

    public static EventShortDto toShortDto(Event event, User user,Long confirmedRequests,Long views) {
        EventShortDto eventShort = EventShortDto.builder()
                                    .annotation(event.getAnnotation())
                                    .category(CategoryMapper.toDto(event.getCategory()))
                                    .confirmedRequests(confirmedRequests)
                                    .eventDate(event.getEventDate().toString())
                                    .id(event.getId())
                                    .initiator(UserMapper.toShortDto(user))
                                    .paid(event.getPaid())
                                    .title(event.getTitle())
                                    .views(views)
                                    .build();
    }
}
