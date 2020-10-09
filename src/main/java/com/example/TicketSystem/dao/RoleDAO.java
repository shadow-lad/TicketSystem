package com.example.TicketSystem.dao;

import com.example.TicketSystem.entities.Role;
import com.example.TicketSystem.entities.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDAO extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleEnum name);
}
