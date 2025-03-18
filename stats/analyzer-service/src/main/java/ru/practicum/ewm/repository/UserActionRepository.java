package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.model.UserAction;

public interface UserActionRepository extends JpaRepository<UserAction, Long> {
}
