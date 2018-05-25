package testSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testSystem.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}