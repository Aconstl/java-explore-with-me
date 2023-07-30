package ru.practicum.category.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public CategoryDto createCategory(@RequestBody @Valid NewCategoryDto category) {
        log.debug("Admin: Категории (Добавление новой категории)");
        return categoryService.createCategory(category);
    }

    @DeleteMapping("/{catId}")
    public void deleteCategory(@PathVariable Long catId) {
        log.debug("Admin: Категории (Удаление категории)");
        categoryService.deleteCategory(catId);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@RequestBody @Valid NewCategoryDto category,
                                      @PathVariable Long catId) {
        log.debug("Admin: Категории (Обновление категории)");
        return categoryService.patchCategory(category,catId);
    }

}
