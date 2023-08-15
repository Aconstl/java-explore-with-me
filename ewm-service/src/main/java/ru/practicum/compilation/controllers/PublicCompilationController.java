package ru.practicum.compilation.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.model.CompilationDto;
import ru.practicum.compilation.service.CompilationService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class PublicCompilationController {

    private final CompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getAllWithFilter(
            @RequestParam(required = false) Boolean pinned,
            @RequestParam(defaultValue = "0") Long from,
            @RequestParam(defaultValue = "10") Long size
    ) {
        log.info("Public: Подборка событий (Получение подборок событий)");
        return compilationService.getAllWithFilter(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilation(
            @PathVariable Long compId
    ) {
        log.info("Public: Подборка событий (Получение подборки событий по его id {})", compId);
        return compilationService.getCompilation(compId);
    }

}
