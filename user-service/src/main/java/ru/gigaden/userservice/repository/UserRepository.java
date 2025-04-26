package ru.gigaden.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gigaden.userservice.entity.User;

/**
 * Репозиторий для работы с юзерами
 * */
public interface UserRepository extends JpaRepository<User, Long> {
}
