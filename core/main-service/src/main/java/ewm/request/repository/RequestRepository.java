package ewm.request.repository;

import ewm.request.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
	List<Request> findAllByRequesterIdAndEvent_id(Long userId, Long eventId);

	List<Request> findAllByRequesterId(Long userId);

	List<Request> findAllByEvent_id(Long eventId);
}
