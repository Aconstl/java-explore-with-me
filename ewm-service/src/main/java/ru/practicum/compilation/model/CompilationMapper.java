package ru.practicum.compilation.model;

import lombok.experimental.UtilityClass;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventMapper;
import ru.practicum.request.repository.RequestRepository;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CompilationMapper {

    public static Compilation fromNewDto(NewCompilationDto newCompilation, List<Event> eventList) {
        Compilation compilation = new Compilation();
            compilation.setEvents(eventList);
            compilation.setPinned(newCompilation.getPinned());
            compilation.setTitle(newCompilation.getTitle());
        return compilation;
    }

    public static CompilationDto toDto(Compilation compilation, RequestRepository requestRepository) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .events(EventMapper.toListShortDto(compilation.getEvents(),requestRepository))
                .build();
    }

    public static List<CompilationDto> toListDto(List<Compilation> compilations, RequestRepository requestRepository) {
        List<CompilationDto> compilationDtos = new ArrayList<>();
        for (Compilation c:compilations) {
            compilationDtos.add(toDto(c, requestRepository));
        }
        return compilationDtos;
    }
}
