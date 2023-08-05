package ru.practicum.event.model.requestStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.practicum.request.model.Status;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@Getter
public class EventRequestStatusUpdateRequest {
    @NotNull
    private List<Long> requestIds;
    @NotNull
    private Status status;
}
