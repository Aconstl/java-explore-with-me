package ru.practicum;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import ru.practicum.customException.model.BadRequestException;

@UtilityClass
public class Pagination {

    public static Pageable setPageable(Long from, Long size) {
        if (from == null || size == null) {
            return Pageable.unpaged();
        } else if (from >= 0 && size > 0) {
            Long page = (from + 1) / size;
            Long pageLast = (from + 1) % size;
            if (pageLast > 0) {
                return PageRequest.of(page.intValue(), size.intValue());
            } else {
                return PageRequest.of(page.intValue() - 1, size.intValue());
            }
        } else {
            throw new BadRequestException("Pagination: Неккоректное количество запрашиваемых значений");
        }
    }

    public static Pageable setPageable(Long from, Long size, String sort) {
        if (from == null || size == null) {
            return Pageable.unpaged();
        } else if (from >= 0 && size > 0) {
            Long page = (from + 1) / size;
            Long pageLast = (from + 1) % size;
            if (pageLast > 0) {
                return PageRequest.of(page.intValue(), size.intValue(), Sort.by(sort));
            } else {
                return PageRequest.of(page.intValue() - 1, size.intValue(), Sort.by(sort));
            }
        } else {
            throw new BadRequestException("Pagination: Неккоректное количество запрашиваемых значений");
        }
    }
}
