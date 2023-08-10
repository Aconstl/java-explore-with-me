package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.Pagination;
import ru.practicum.port.StatisticPort;
import ru.practicum.compilation.model.*;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.customException.model.NotFoundException;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.request.repository.RequestRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;
    private final RequestRepository requestRepository;
    private final StatisticPort stats;

    public CompilationDto createCompilation(NewCompilationDto newCompilation) {
        List<Event> eventList = eventRepository.findAllByIdIn(newCompilation.getEvents() != null ?
        newCompilation.getEvents() : new ArrayList<>());

        Compilation compilation = compilationRepository.save(CompilationMapper.fromNewDto(newCompilation,eventList));
        return CompilationMapper.toDto(compilation,requestRepository,stats);
    }

    public void deleteCompilation(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                //Подборка не найдена - 404;
                () -> new NotFoundException("Подборка не найдена")
        );
        compilationRepository.delete(compilation);
    }

    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest updateCompilation) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                //Подборка не найдена - 404;
                () -> new NotFoundException("Подборка не найдена")
        );

        if (updateCompilation.getTitle() != null) {
            compilation.setTitle(updateCompilation.getTitle());
        }
        if (updateCompilation.getPinned() != null) {
            compilation.setPinned(updateCompilation.getPinned());
        }
        if (updateCompilation.getEvents() != null) {
            List<Event> eventList = eventRepository.findAllByIdIn(updateCompilation.getEvents());
            compilation.setEvents(eventList);
        }
        return CompilationMapper.toDto(compilationRepository.save(compilation),requestRepository,stats);

    }

    public List<CompilationDto> getAllWithFilter(Boolean pinned, Long from, Long size) {
        Pageable pageable = Pagination.setPageable(from,size);
        List<Compilation> compilations = (pinned == null) ?
                compilationRepository.findAll(pageable).getContent() :
                compilationRepository.findAllByPinned(pinned,pageable);

        return CompilationMapper.toListDto(compilations,requestRepository,stats);
    }

    public CompilationDto getCompilation(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                //Подборка не найдена - 404;
                () -> new NotFoundException("Подборка не найдена")
        );
        return CompilationMapper.toDto(compilation,requestRepository,stats);
    }

}
