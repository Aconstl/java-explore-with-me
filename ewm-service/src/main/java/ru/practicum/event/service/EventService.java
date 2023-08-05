package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.Pagination;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.customException.model.BadRequestException;
import ru.practicum.customException.model.ConflictException;
import ru.practicum.customException.model.NotFoundException;
import ru.practicum.event.model.*;

import org.springframework.data.domain.Pageable;
import ru.practicum.event.model.dto.*;
import ru.practicum.event.model.requestStatus.EventRequestStatusUpdateRequest;
import ru.practicum.event.model.requestStatus.EventRequestStatusUpdateResult;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.repository.LocationRepository;
import ru.practicum.request.model.EventRequest;
import ru.practicum.request.model.RequestMapper;
import ru.practicum.request.model.Status;
import ru.practicum.request.model.dto.ParticipationRequestDto;
import ru.practicum.request.repository.RequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.List;
import java.util.Set;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final RequestRepository requestRepository;

    private final LocationRepository locationRepository;

    //PUBLIC
    public List<EventShortDto>  getEventWithFilter(String text,
                                                   Set<Long> categories,
                                                   Boolean paid,
                                                   LocalDateTime rangeStart,
                                                   LocalDateTime rangeEnd,
                                                   Boolean onlyAvailable,
                                                   SortEvent sort,
                                                   Long from,
                                                   Long size) {
        Pageable pageable;
        if (sort == SortEvent.EVENT_DATE) {
            pageable = Pagination.setPageable(from,size,"eventDate");
        } else {
            pageable = Pagination.setPageable(from,size);
        }

        List<Event> events;
        if (onlyAvailable) {
            events = eventRepository.getAvailableEventsByParam(text, categories, paid, rangeStart, rangeEnd, pageable);
        } else {
            events = eventRepository.getEventsByParam(text, categories, paid, rangeStart, rangeEnd, pageable);
        }

        return EventMapper.toListShortDto(events,requestRepository);
    }

    public EventFullDto getEventById(Long id) {
        if (id != null) {
            Event event = eventRepository.findById(id)
                    //Не найден - 404
                    .orElseThrow(() -> new NotFoundException("События с указаным id не существует"));
            if (event.getState() != State.PUBLISHED) {
                //Недоступнос - 404
                throw new NotFoundException("Событие еще не опубликовано");
            }
            return EventMapper.toFullDto(event,requestRepository);
        } else {
            //Не корректно - 400
            throw new BadRequestException("id категории указан неккоректно");
        }
    }

    //PRIVATE
    public List<EventShortDto> getUserEvents(Long id, Long from, Long size) {
        Pageable pageable = Pagination.setPageable(from,size);
        List<Event> eventList = eventRepository.findByInitiatorId(id,pageable);
        return EventMapper.toListShortDto(eventList,requestRepository);
    }

    public EventFullDto createEvent(Long userId, NewEventDto newEvent) {
        LocalDateTime actualPublicationTime = LocalDateTime.now().plusHours(2);
        if (newEvent.getEventDate().isBefore(actualPublicationTime)) {
            //Событие не удовлетворяет правилам создания  - 409
            throw new ConflictException("Время мероприятия должно быть минимум через 2 часа");
        }

        User initiator = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указаным id не найден"));

        Category category = categoryRepository.findById(newEvent.getCategory())
                //Если не найден с данным id - 404
                .orElseThrow(() -> new NotFoundException("Категории с указаным id не существует"));

        Event event = new Event();
            event.setInitiator(initiator);
            event.setAnnotation(newEvent.getAnnotation());
            event.setCategory(category);
            event.setDescription(newEvent.getDescription());
            event.setCreatedOn(LocalDateTime.now());
            event.setEventDate(newEvent.getEventDate());
            event.setLocation(newEvent.getLocation());
            event.setPaid(newEvent.getPaid());
            event.setParticipantLimit(newEvent.getParticipantLimit() != null ? newEvent.getParticipantLimit() : 0L);
            event.setRequestModeration(newEvent.getRequestModeration() != null ? newEvent.getRequestModeration() : true);
            event.setTitle(newEvent.getTitle());
            event.setState(State.PENDING);
            event.setViews(0L);
            event.setPublishedOn(null);

         event = eventRepository.save(event);
        return EventMapper.toFullDto(event,requestRepository);
    }

    public EventFullDto getUserEvent(Long userId, Long eventId) {
        Event event = eventRepository.findByInitiatorIdAndId(userId,eventId)
                //Не найден - 404
                .orElseThrow(() -> new NotFoundException("События с указаным id не существует"));

        return EventMapper.toFullDto(event,requestRepository);
    }

    public EventFullDto updateEvent(Long userId, Long eventId, UpdateEventUserRequest updEvent) {
        Event event = eventRepository.findByInitiatorIdAndId(userId,eventId)
                //Не найден - 404
                .orElseThrow(() -> new NotFoundException("События с указаным id не существует"));
        if (event.getState().equals(State.PUBLISHED)) {
            //Событие не удовлетворяет правилам редактирования - 409
            throw new ConflictException("Нельзя изменить опубликованное событие");
        }

        if (updEvent == null) {
            return EventMapper.toFullDto(event, requestRepository);
        }

        String annotation = event.getAnnotation().equals(updEvent.getAnnotation()) ?
                event.getAnnotation() : updEvent.getAnnotation();

        Long categoryId = event.getCategory().getId().equals(updEvent.getCategory()) ?
                event.getCategory().getId() : updEvent.getCategory();

        String description = event.getDescription().equals(updEvent.getDescription()) ?
                event.getDescription() : updEvent.getDescription();

        LocalDateTime eventDate = event.getEventDate().isEqual(updEvent.getEventDate()) ?
                event.getEventDate() : updEvent.getEventDate();
        LocalDateTime actualPublicationTime = LocalDateTime.now().plusHours(2);
        if (eventDate.isBefore(actualPublicationTime)) {
            //Событие не удовлетворяет правилам создания  - 409
            throw new ConflictException("Время мероприятия должно быть минимум через 2 часа");
        }

        Location location = event.getLocation();
        if (location.getLat() !=  updEvent.getLocation().getLat()) {  //lat
            location.setLat(updEvent.getLocation().getLat());
        }
        if (location.getLon() !=  updEvent.getLocation().getLon()) {  //lon
            location.setLon(updEvent.getLocation().getLon());
        }

        Boolean paid = event.getPaid().equals(updEvent.getPaid()) ?
                event.getPaid() : updEvent.getPaid();

        Long participantLimit = event.getParticipantLimit().equals(updEvent.getParticipantLimit()) ?
                event.getParticipantLimit() : updEvent.getParticipantLimit();

        Boolean requestModeration = event.getRequestModeration().equals(updEvent.getRequestModeration()) ?
                event.getRequestModeration() : updEvent.getRequestModeration();

        String title = event.getTitle().equals(updEvent.getTitle()) ?
                event.getTitle() : updEvent.getTitle();

        State state;
        if (updEvent.getStateAction().equals(StateUserAction.SEND_TO_REVIEW)) {  //stateAction
            state = State.PENDING;
        } else if (updEvent.getStateAction().equals(StateUserAction.CANCEL_REVIEW)) {
            state = State.CANCELED;
        } else {
            throw new BadRequestException("Ошибка чтения изменения состояния события");
        }

        //Обновление БД локации и БД события
        Location loc =  locationRepository.updateEvent(location.getId(),location.getLat(),location.getLon()).get();

        Event req = eventRepository.updateEvent(eventId,annotation,categoryId,
                description,eventDate,paid,
                participantLimit,requestModeration,title,state).get();

        return EventMapper.toFullDto(req,requestRepository);
    }

    public List<ParticipationRequestDto> getRequestsInUserEvent(Long userId, Long eventId) {
        Event event = eventRepository.findByInitiatorIdAndId(userId,eventId)
                //Не найден - 404
                .orElseThrow(() -> new NotFoundException("События с указаным id не существует"));
        List<EventRequest> requests = requestRepository.findByEventId(eventId);
        return RequestMapper.toListDto(requests);
    }

    public EventRequestStatusUpdateResult changeStatusEvent(Long userId, Long eventId,
                                                            EventRequestStatusUpdateRequest statusUpdateRequest) {
        Event event = eventRepository.findByInitiatorIdAndId(userId,eventId)
                //Не найден - 404
                .orElseThrow(() -> new NotFoundException("События с указаным id не существует"));

     //   if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
        if (event.getRequestModeration()) { // если предмоедарция заявок отключена - подтверждение заявок не требуется
            int countConfirmed = requestRepository.findAllByEventIdAndStatus(eventId,Status.CONFIRMED).size();

            if (countConfirmed >= event.getParticipantLimit()) {
                //достигнут лимит заявок - 409
                throw new ConflictException("Количество подтвержденных заявок достигает лимит");
            }

            List<EventRequest> requests = requestRepository.findAllByEventIdAndIdIn(eventId,statusUpdateRequest.getRequestIds());
            for (EventRequest r : requests) {
                if (!r.getStatus().equals(Status.PENDING)) {
                    //статус можно изменить только у заявок, находящихся в состоянии ожидания - 409
                    throw new ConflictException("Заявка должна иметь статус ожидания");
                }
                if (countConfirmed < event.getParticipantLimit()) {
                    r.setStatus(statusUpdateRequest.getStatus());
                    countConfirmed++;
                } else {
                    //если лимит заявок исчерапн - остальные заявки отклоняются
                    r.setStatus(Status.REJECTED);
                }
                requestRepository.changeStatusRequest(r.getRequester().getId(),
                        r.getId(), r.getStatus().toString());
            }
        }
        List<EventRequest> confirmedRequests = requestRepository
                .findAllByEventIdAndIdInAndStatus(eventId,statusUpdateRequest.getRequestIds(),Status.CONFIRMED);
        List<EventRequest> rejectedRequests = requestRepository
                .findAllByEventIdAndIdInAndStatus(eventId,statusUpdateRequest.getRequestIds(),Status.REJECTED);
        return new EventRequestStatusUpdateResult(RequestMapper.toListDto(confirmedRequests),
                RequestMapper.toListDto(rejectedRequests));
    }

    //ADMIN
    public List<EventFullDto> getEvents(Set<Long> userIds,
                                        Set<String> states,
                                        Set<Long> categoryIds,
                                        LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd,
                                        Long from,
                                        Long size
                                        ) {
        Pageable pageable = Pagination.setPageable(from,size);
        List<Event> events = eventRepository.getEventFilterForAdmin(userIds,states,
                categoryIds,rangeStart,rangeEnd,pageable);

        return EventMapper.toListFulDto(events,requestRepository);

    //    throw new UnsupportedOperationException("Не реализован");
    }

    public EventFullDto updateAdminEvent(Long eventId, UpdateEventAdminRequest updEventAdm) {
        Event event = eventRepository.findById(eventId)
                //Не найден - 404
                .orElseThrow(() -> new NotFoundException("События с указаным id не существует"));

   //     if (!event.getState().equals(State.PENDING)) {
   //         //Событие должно быть в состоянии ожидании публикации
   //         throw new ConflictException("Событие должно быть в состоянии ожидании публикации");
   //     }

        String annotation = event.getAnnotation().equals(updEventAdm.getAnnotation()) ?
                event.getAnnotation() : updEventAdm.getAnnotation();

        Long categoryId = event.getCategory().getId().equals(updEventAdm.getCategory()) ?
                event.getCategory().getId() : updEventAdm.getCategory();

        String description = event.getDescription().equals(updEventAdm.getDescription()) ?
                event.getDescription() : updEventAdm.getDescription();

        LocalDateTime eventDate = event.getEventDate().isEqual(updEventAdm.getEventDate()) ?
                event.getEventDate() : updEventAdm.getEventDate();
        LocalDateTime actualPublicationTime = LocalDateTime.now().plusHours(1);
        if (eventDate.isBefore(actualPublicationTime)) {
            //Событие не удовлетворяет правилам создания  - 409
            throw new ConflictException("Время мероприятия должно быть минимум через 1 час");
        }

        Location location = event.getLocation();
        if (location.getLat() !=  updEventAdm.getLocation().getLat()) {  //lat
            location.setLat(updEventAdm.getLocation().getLat());
        }
        if (location.getLon() !=  updEventAdm.getLocation().getLon()) {  //lon
            location.setLon(updEventAdm.getLocation().getLon());
        }

        Boolean paid = event.getPaid().equals(updEventAdm.getPaid()) ?
                event.getPaid() : updEventAdm.getPaid();

        Long participantLimit = event.getParticipantLimit().equals(updEventAdm.getParticipantLimit()) ?
                event.getParticipantLimit() : updEventAdm.getParticipantLimit();

        Boolean requestModeration = event.getRequestModeration().equals(updEventAdm.getRequestModeration()) ?
                event.getRequestModeration() : updEventAdm.getRequestModeration();

        String title = event.getTitle().equals(updEventAdm.getTitle()) ?
                event.getTitle() : updEventAdm.getTitle();

        State state = event.getState();
        LocalDateTime publishedOn = event.getPublishedOn();
        if (updEventAdm.getStateAction() != null) {
            if (state.equals(State.PUBLISHED) && updEventAdm.getStateAction().equals(StateAdminAction.REJECT_EVENT)) {
                //Событие можно отклонить, только если оно еще не опубликовано (ошибка 409)
                throw new ConflictException("Публикацию события нельзя отклонить,оно уже опубликовано");
            }
            if (!state.equals(State.PENDING) && updEventAdm.getStateAction().equals(StateAdminAction.PUBLISH_EVENT)) {
                //Событие можно публиковать, только если оно в состоянии ожидания публикации (ошибка 409)
                throw new ConflictException("Публиковать разрешено,если оно ожидает публикации");
            }

            if (updEventAdm.getStateAction().equals(StateAdminAction.PUBLISH_EVENT)) {  //stateAction
                state = State.PUBLISHED;
                publishedOn = LocalDateTime.now();
            } else if (updEventAdm.getStateAction().equals(StateAdminAction.REJECT_EVENT)) {
                state = State.CANCELED;
            } else {
                throw new BadRequestException("Ошибка чтения изменения состояния события");
            }
        }
        //Обновление БД локации и БД события
        Location loc =  locationRepository.updateEvent(location.getId(),location.getLat(),location.getLon()).get();

        Event req = eventRepository.updateEventAdm(eventId,annotation,categoryId,
                description,eventDate,paid,
                participantLimit,requestModeration,title,state,publishedOn).get();

        return EventMapper.toFullDto(req,requestRepository);
    }

}
