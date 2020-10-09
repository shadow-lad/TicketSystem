package com.example.TicketSystem.dao;

import com.example.TicketSystem.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieDAO extends JpaRepository<Movie, Long> {
}
