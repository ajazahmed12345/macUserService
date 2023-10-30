package com.ajaz.userservice.repositories;

import com.ajaz.userservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {
}
