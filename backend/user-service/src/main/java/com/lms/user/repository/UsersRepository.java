package com.lms.user.repository;

import java.util.Optional;

import com.lms.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
