package com.ajaz.userservice.repositories;

import com.ajaz.userservice.models.Session;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.FluentQuery;

import java.util.Optional;
import java.util.function.Function;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findByUser_IdAndToken(Long userId, String token);
}
