package ru.practicum.ewm.dto.event;

import ru.practicum.ewm.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class EventRequestStatusUpdateRequest {
	private List<Long> requestIds;
	private RequestStatus status;
}
