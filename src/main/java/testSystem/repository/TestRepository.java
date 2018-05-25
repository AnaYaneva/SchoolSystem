package testSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import testSystem.entity.Test;

public interface TestRepository extends JpaRepository<Test, Integer> {
}
