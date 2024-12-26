package com.example.spring_security_jwt.repository;

import com.example.spring_security_jwt.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface EventRepository extends JpaRepository<Event, Long> {
}
