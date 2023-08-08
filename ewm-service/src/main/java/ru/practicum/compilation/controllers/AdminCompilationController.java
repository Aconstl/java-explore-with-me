package ru.practicum.compilation.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.model.CompilationDto;
import ru.practicum.compilation.model.NewCompilationDto;
import ru.practicum.compilation.model.UpdateCompilationRequest;
import ru.practicum.compilation.service.CompilationService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
@Validated
public class AdminCompilationController {

    private final CompilationService compilationService;

    @PostMapping
    public CompilationDto createCompilation(@RequestBody @Valid NewCompilationDto newCompilation) {
        log.info("Admin: Подборка событий (Добавление новой подборки)");
        return compilationService.createCompilation(newCompilation);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable Long compId) {
        log.info("Admin: Подборка событий (Удаление подборки)");
        compilationService.deleteCompilation(compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable Long compId,
                                  @RequestBody @Valid UpdateCompilationRequest updCompilation) {
        log.info("Admin: Подборка событий (Обновление подборки)");
        return compilationService.updateCompilation(compId,updCompilation);
    }

}
