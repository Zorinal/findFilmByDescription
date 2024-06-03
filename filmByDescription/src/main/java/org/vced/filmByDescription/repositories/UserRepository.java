package org.vced.filmByDescription.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vced.filmByDescription.models.User;

import java.util.Optional;

// Прослойка между java и бд, со старта уже реализованы базовые CRUD операции
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
