package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.Pagination;
import ru.practicum.user.model.NewUserRequest;
import ru.practicum.user.model.User;
import ru.practicum.user.model.UserDto;
import ru.practicum.user.model.UserMapper;
import ru.practicum.user.repository.UserRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> getUsers(List<Long> ids, Long from, Long size) {
        Pageable pageable = Pagination.setPageable(from, size);
        return userRepository.findByIdIn(ids,pageable).stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    public UserDto createUser(NewUserRequest newUser) {
        User user = userRepository.save(UserMapper.fromCreateUser(newUser));
        return UserMapper.toDto(user);
    }

    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            throw new IllegalArgumentException("Пользователь для удаления не найден");
        });
    }

}
