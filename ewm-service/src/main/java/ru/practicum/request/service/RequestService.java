package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.request.model.EventRequest;
import ru.practicum.request.model.RequestMapper;
import ru.practicum.request.model.Status;
import ru.practicum.request.model.dto.ParticipationRequestDto;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    public List<ParticipationRequestDto> getUserRequest(Long userId) {
        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с указаным id не найден"));
        List<EventRequest> requests = requestRepository.findByRequesterId(userId);
        return RequestMapper.toListDto(requests);
    }

    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь с указаным id не найден"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Событие с указаным id не найдено"));

        EventRequest request = new EventRequest();
            request.setRequester(requester);
            request.setEvent(event);
            request.setCreated(LocalDateTime.now());
            request.setStatus(Status.PENDING);

        request = requestRepository.save(request);

        return RequestMapper.toDto(request);
    }

    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
      //  User requester = userRepository.findById(userId)
      //          .orElseThrow(() -> new IllegalArgumentException("Пользователь с указаным id не найден"));

        EventRequest request = requestRepository.findByRequesterIdAndId(userId,requestId).orElseThrow(
                () -> new IllegalArgumentException("Запроса с такими параметрами не существует")
        );
        request.setStatus(Status.REJECTED);

        request = requestRepository.changeStatusRequest(userId,requestId,request.getStatus().toString()).get();
        return RequestMapper.toDto(request);
    }
}
