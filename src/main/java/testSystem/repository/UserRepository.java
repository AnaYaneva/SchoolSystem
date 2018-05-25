package testSystem.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import testSystem.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}