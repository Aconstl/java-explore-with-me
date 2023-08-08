package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.customException.model.ConflictException;
import ru.practicum.customException.model.NotFoundException;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;
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
  //      if (userId != null) {
            User requester = userRepository.findById(userId)
                    .orElseThrow(() -> new NotFoundException("Пользователь с указаным id не найден"));
  //      } else {
   //         throw new BadRequestException("id Пользователя неккоретно");
   //     }
        List<EventRequest> requests = requestRepository.findByRequesterId(userId);
        return RequestMapper.toListDto(requests);
    }

    public ParticipationRequestDto createRequest(Long userId, Long eventId) {
        User requester = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указаным id не найден"));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с указаным id не найдено"));

        //нельзя добавить повторный запрос (Ожидается код ошибки 409)
        if (requestRepository.findByRequesterIdAndId(userId, eventId).isPresent()) {
            throw new ConflictException("Повторно запрос создать запрещено");
        }

        //инициатор события не может добавить запрос на участие в своём событии (Ожидается код ошибки 409)
        if (event.getInitiator().getId() == requester.getId()) {
            throw new ConflictException("Пользователь не может оставлять заявку на собственное событие");
        }

        //нельзя участвовать в неопубликованном событии (Ожидается код ошибки 409)
        if (event.getState() != State.PUBLISHED) {
            throw new ConflictException("Данное событие еще не опубликовано");
        }

        //если у события достигнут лимит запросов на участие - необходимо вернуть ошибку (Ожидается код ошибки 409)
        List<EventRequest> requests = requestRepository.findAllByEventId(eventId);
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit() <= requests.size()) {
            throw new ConflictException("Достигнут лимит запросов на участие");
        }

        EventRequest request = new EventRequest();
            request.setRequester(requester);
            request.setEvent(event);
            request.setCreated(LocalDateTime.now());
        //если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного
            if (event.getRequestModeration()) {
                request.setStatus(Status.PENDING);
            } else {
                request.setStatus(Status.CONFIRMED);
            }

        request = requestRepository.save(request);

        return RequestMapper.toDto(request);
    }

    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
      //  User requester = userRepository.findById(userId)
      //          .orElseThrow(() -> new IllegalArgumentException("Пользователь с указаным id не найден"));

        EventRequest request = requestRepository.findByRequesterIdAndId(userId,requestId).orElseThrow(
                () -> new NotFoundException("Запроса с такими параметрами не существует")
        );
        request.setStatus(Status.REJECTED);

        request = requestRepository.changeStatusRequest(userId,requestId,request.getStatus().toString()).get();
        return RequestMapper.toDto(request);
    }
}
