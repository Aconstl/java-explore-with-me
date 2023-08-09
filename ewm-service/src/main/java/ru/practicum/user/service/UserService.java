package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.Pagination;
import ru.practicum.customException.model.NotFoundException;
import ru.practicum.user.model.NewUserRequest;
import ru.practicum.user.model.User;
import ru.practicum.user.model.UserDto;
import ru.practicum.user.model.UserMapper;
import ru.practicum.user.repository.UserRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> getUsers(List<Long> ids, Long from, Long size) {
        Pageable pageable = Pagination.setPageable(from, size);
        List<User> users;
        if (ids == null || ids.isEmpty()) {
            users = userRepository.findAll(pageable).getContent();
        } else {
            users = userRepository.findByIdIn(ids,pageable);
        }
        return users.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }

    public UserDto createUser(NewUserRequest newUser) {
        User user = userRepository.save(UserMapper.fromCreateUser(newUser));
        return UserMapper.toDto(user);
    }

    public void deleteUser(Long userId) {
        userRepository.findById(userId).ifPresentOrElse(userRepository::delete, () -> {
            //Пользователь не найден- 404
            throw new NotFoundException("Пользователь для удаления не найден");
        });
    }

}
