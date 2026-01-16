package by.katekhreshkova.task_microservice.services;

import by.katekhreshkova.task_microservice.contracts.req.TaskRequest;
import by.katekhreshkova.task_microservice.contracts.resp.TaskResponse;
import by.katekhreshkova.task_microservice.converters.TaskConverter;
import by.katekhreshkova.task_microservice.models.Task;
import by.katekhreshkova.task_microservice.models.TaskStatus;
import by.katekhreshkova.task_microservice.repositories.TaskRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import static java.time.LocalDateTime.*;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskConverter taskConverter;

    public TaskResponse createTask(@NotNull final UUID userId, @NotNull final TaskRequest taskRequest){
        Task taskToSave = new Task();
        taskToSave.setUserId(userId);
        taskToSave.setTitle(taskRequest.title());
        taskToSave.setDescription(taskRequest.description());
        taskToSave.setStatus(TaskStatus.PENDING);
        taskToSave.setCreatedAt(now());
        return taskConverter.modelToContract(taskRepository.save(taskToSave));
    }
    public List<TaskResponse> findAll(UUID userId) {
        return taskRepository.findAllByUserIdAndDeletedFalse(userId)
                .stream()
                .map(taskConverter::modelToContract)
                .toList();
    }

}
