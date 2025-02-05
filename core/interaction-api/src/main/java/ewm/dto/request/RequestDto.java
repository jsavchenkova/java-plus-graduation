package ewm.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ewm.model.RequestStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class RequestDto {
	private String created;
	private Long event;
	private Long id;
	private Long requester;
	private RequestStatus status;
}
