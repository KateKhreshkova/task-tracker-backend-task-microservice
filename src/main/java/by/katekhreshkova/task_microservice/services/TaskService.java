package by.katekhreshkova.task_microservice.services;

import by.katekhreshkova.task_microservice.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

}
