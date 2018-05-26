package schoolSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolSystemApplication {
	//ROLE_ADMIN must be added manualy in DB, and a user must be defined as admin in DB users_roles table

	public static void main(String[] args) {
		SpringApplication.run(SchoolSystemApplication.class, args);
	}
}
