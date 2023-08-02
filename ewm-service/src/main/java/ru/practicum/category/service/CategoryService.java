package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.Pagination;
import ru.practicum.category.model.Category;
import ru.practicum.category.model.CategoryDto;
import ru.practicum.category.model.CategoryMapper;
import ru.practicum.category.model.NewCategoryDto;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.customException.model.ConflictException;

import org.springframework.data.domain.Pageable;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDto> getCategories(Long from, Long size) {
        Pageable pageable = Pagination.setPageable(from,size);
        List<Category> listCategories = categoryRepository.findAll(pageable).getContent();
        return CategoryMapper.toListDto(listCategories);
    }

    public CategoryDto getCategory(Long catId) {
        if (catId != null) {
            Category category = categoryRepository.findById(catId)
                    .orElseThrow(() -> new IllegalArgumentException("Категории с указаным id не существует"));
            return CategoryMapper.toDto(category);
        } else {
            throw new NullPointerException("id категории указан неккоректно");
        }
    }

    public CategoryDto createCategory(NewCategoryDto newCategory) {
       // categoryRepository.findByName(newCategory.getName()).isEmpty();
        Category category = new Category(newCategory.getName());
        category = categoryRepository.save(category);
        return CategoryMapper.toDto(category);
    }

    public void deleteCategory(Long catId) {
        if (catId != null) {
            categoryRepository.findById(catId).ifPresentOrElse(categoryRepository::delete, () -> {
                throw new IllegalArgumentException("Категории с указанным id не существует");
            });
        } else {
            throw new NullPointerException("id категории указан неккоректно");
        }
    }

    public CategoryDto patchCategory(NewCategoryDto updCategory, Long catId) {

        categoryRepository.findByName(updCategory.getName()).ifPresent((x) -> {
            if (x.getId() != catId) {
                throw new ConflictException("Категория с таким именем уже существует");
            }
        });

        Category category = categoryRepository.findById(catId).orElseThrow(() ->
                new IllegalArgumentException("Категории с указанным id не существует"));

        if (updCategory.getName() != null) {
            category = (categoryRepository.updateCategoryName(updCategory.getName(),catId)).get();
        }

        return CategoryMapper.toDto(category);
    }
}
