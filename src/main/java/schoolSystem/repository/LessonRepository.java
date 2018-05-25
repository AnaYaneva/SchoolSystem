package schoolSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import schoolSystem.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
}
