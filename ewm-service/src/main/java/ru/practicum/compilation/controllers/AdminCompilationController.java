package ru.practicum.compilation.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@RequestBody @Valid NewCompilationDto newCompilation) {
        log.info("Admin: Подборка событий (Добавление новой подборки)");
        return compilationService.createCompilation(newCompilation);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        log.info("Admin: Подборка событий (Удаление подборки c id {})", compId);
        compilationService.deleteCompilation(compId);
        log.info("Admin: Подборка событий c id {} удалена)", compId);
    }

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilation(@PathVariable Long compId,
                                  @RequestBody @Valid UpdateCompilationRequest updCompilation) {
        log.info("Admin: Подборка событий (Обновление подборки с id {})", compId);
        CompilationDto compilationDto = compilationService.updateCompilation(compId,updCompilation);
        log.info("Admin: Подборка с id {} обновлена", compId);
        return compilationDto;
    }

}
