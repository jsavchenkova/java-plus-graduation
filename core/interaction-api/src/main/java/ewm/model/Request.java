package ewm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime created;

//	@ManyToOne(fetch = FetchType.LAZY)
	@Column(name = "event_id")
	private Long eventId;

//	@ManyToOne(fetch = FetchType.LAZY)
	@Column(name = "requester_id")
	private Long requesterId;

	private RequestStatus status;
}
