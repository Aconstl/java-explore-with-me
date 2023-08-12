package ru.practicum.category.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.model.CategoryDto;
import ru.practicum.category.model.NewCategoryDto;
import ru.practicum.category.service.CategoryService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Validated
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto category) {
        log.info("Admin: Категории (Добавление новой категории)");
        CategoryDto categoryDto = categoryService.createCategory(category);
        log.info("Admin: Категория создана успешно");
        return categoryDto;
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long catId) {
        log.info("Admin: Категории (Удаление категории с id {})", catId);
        categoryService.deleteCategory(catId);
        log.info("Admin: Категория с id {} удалена)", catId);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@RequestBody @Valid NewCategoryDto category,
                                      @PathVariable Long catId) {
        log.info("Admin: Категории (Обновление категории с id {})", catId);
        CategoryDto categoryDto = categoryService.patchCategory(category,catId);
        log.info("Admin: Категория с id {} обновлена успешно)", catId);
        return categoryDto;
    }

}
