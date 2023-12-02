package com.olidev.pe.TutorialJWT.model.dao;

import com.olidev.pe.TutorialJWT.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
