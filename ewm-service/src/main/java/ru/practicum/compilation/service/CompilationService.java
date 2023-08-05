package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.model.CompilationDto;
import ru.practicum.compilation.model.NewCompilationDto;
import ru.practicum.compilation.model.UpdateCompilationRequest;

@RequiredArgsConstructor
@Service
public class CompilationService {

    public CompilationDto createCompilation(NewCompilationDto newCompilation) {
        throw new UnsupportedOperationException("Не реализован");
    }

    public void deleteCompilation(Long compId) {
        throw new UnsupportedOperationException("Не реализован");
    }

    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest updateCompilation) {
        throw new UnsupportedOperationException("Не реализован");
    }
}
