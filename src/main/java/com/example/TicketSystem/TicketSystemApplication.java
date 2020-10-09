package com.example.TicketSystem;

import com.example.TicketSystem.dao.RoleDAO;
import com.example.TicketSystem.entities.Role;
import com.example.TicketSystem.entities.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicketSystemApplication implements ApplicationRunner {

	@Autowired
	RoleDAO roleDAO;

	public static void main(String[] args) {
		SpringApplication.run(TicketSystemApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		try {
			roleDAO.save(new Role(RoleEnum.ROLE_ADMIN));
		} catch (Exception ignore) {
			// Exception thrown when role already exists
		}
	}
}
