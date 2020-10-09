package com.example.TicketSystem.dao;

import com.example.TicketSystem.entities.Timings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimingDAO extends JpaRepository<Timings, Long> {

    Optional<Timings> findByEndTimeBetween(long small, long big);
    Optional<Timings> findByStartTimeBetween(long small, long big);
    Optional<Timings>

}
