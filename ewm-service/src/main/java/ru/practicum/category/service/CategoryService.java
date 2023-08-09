package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.Pagination;
import ru.practicum.category.model.Category;
import ru.practicum.category.model.CategoryDto;
import ru.practicum.category.model.CategoryMapper;
import ru.practicum.category.model.NewCategoryDto;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.customException.model.BadRequestException;
import ru.practicum.customException.model.ConflictException;

import org.springframework.data.domain.Pageable;
import ru.practicum.customException.model.NotFoundException;
import ru.practicum.event.repository.EventRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    private final EventRepository eventRepository;

    //PUBLIC
    public List<CategoryDto> getCategories(Long from, Long size) {
        Pageable pageable = Pagination.setPageable(from,size);
        List<Category> listCategories = categoryRepository.findAll(pageable).getContent();
        return CategoryMapper.toListDto(listCategories);
    }

    public CategoryDto getCategory(Long catId) {
        if (catId != null) {
            Category category = categoryRepository.findById(catId)
                    //Если не найден с данным id - 404
                    .orElseThrow(() -> new NotFoundException("Категории с указаным id не существует"));
            return CategoryMapper.toDto(category);
        } else {
            //некорректно составлен запрос - 400
            throw new BadRequestException("id категории указан неккоректно");
        }
    }

    //ADMIN
    public CategoryDto createCategory(NewCategoryDto newCategory) {
        categoryRepository.findByName(newCategory.getName()).ifPresent((x) -> {
                //нарушение целостности данных - 409
                throw new ConflictException("Категория с таким именем уже существует");
        });

       // categoryRepository.findByName(newCategory.getName()).isEmpty();
        Category category = new Category(newCategory.getName());
        category = categoryRepository.save(category);
        return CategoryMapper.toDto(category);
    }

    public void deleteCategory(Long catId) {
        if (catId != null) {
            if (!eventRepository.findByCategoryId(catId).isEmpty()) {
                //нарушение целостности данных - 409
                throw new ConflictException("Существуют события, связанные с категорией");
            }
            categoryRepository.findById(catId).ifPresentOrElse(categoryRepository::delete, () -> {
                //Если не найден с данным id - 404
                throw new NotFoundException("Категории с указанным id не существует");
            });
        } else {
            //некорректно составлен запрос - 400
            throw new BadRequestException("id категории указан неккоректно");
        }
    }

    public CategoryDto patchCategory(NewCategoryDto updCategory, Long catId) {
        categoryRepository.findByName(updCategory.getName()).ifPresent((x) -> {
            if (x.getId() != catId) {
                //нарушение целостности данных - 409
                throw new ConflictException("Категория с таким именем уже существует");
            }
        });

        Category category = categoryRepository.findById(catId).orElseThrow(() ->
                //Если не найден с данным id - 404
                new NotFoundException("Категории с указанным id не существует"));

        if (updCategory.getName() != null) category.setName(updCategory.getName());

        category = categoryRepository.saveAndFlush(category);
        return CategoryMapper.toDto(category);
    }
}
