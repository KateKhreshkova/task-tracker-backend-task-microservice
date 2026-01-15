package by.katekhreshkova.task_microservice.repositories;

import by.katekhreshkova.task_microservice.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
