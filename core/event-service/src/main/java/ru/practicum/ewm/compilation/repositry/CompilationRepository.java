package ru.practicum.ewm.compilation.repositry;

import ru.practicum.ewm.compilation.model.Compilation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
	List<Compilation> findByPinned(Boolean pinned, Pageable pageable);
}
