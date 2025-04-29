package ru.gigaden.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gigaden.userservice.entity.User;

import java.util.Collection;

/**
 * A repository for working with users
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Collection<User> findAllByCompanyId(Long companyId);
}
