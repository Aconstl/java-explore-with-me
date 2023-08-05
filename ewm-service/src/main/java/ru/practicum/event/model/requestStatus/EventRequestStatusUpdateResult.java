package ru.practicum.event.model.requestStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.request.model.dto.ParticipationRequestDto;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventRequestStatusUpdateResult {

   private List<ParticipationRequestDto> confirmedRequests;
   private List<ParticipationRequestDto> rejectedRequests;
}
