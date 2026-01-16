package by.katekhreshkova.task_microservice.repositories;

import by.katekhreshkova.task_microservice.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findAllByUserIdAndDeletedFalse(UUID userId);

    @Query("""
                select t from Task t
                where t.id = :id
                  and t.userId = :userId
                  and t.deleted = false
            """)
    Optional<Task> findByIdAndUserIdAndDeletedFalse(UUID id, UUID userId);

}
