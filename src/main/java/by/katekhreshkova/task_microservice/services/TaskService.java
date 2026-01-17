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
        return taskConverter.modelToResponse(taskRepository.save(taskToSave));
    }

    public List<TaskResponse> findAll(@NotNull final UUID userId) {
        return taskRepository.findAllByUserIdAndDeletedFalse(userId)
                .stream()
                .map(taskConverter::modelToResponse)
                .toList();
    }

    public TaskResponse update(@NotNull final UUID userId, @NotNull final UUID taskId, @NotNull final TaskRequest req) {
        Task taskToUpdate = taskRepository.findByIdAndUserIdAndDeletedFalse(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        taskToUpdate.setTitle(req.title());
        taskToUpdate.setDescription(req.description());
        taskToUpdate.setUpdatedAt(now());
        taskRepository.save(taskToUpdate);

        return taskConverter.modelToResponse(taskToUpdate);
    }

    public void delete(@NotNull final UUID userId, @NotNull final UUID taskId) {
        Task taskToDelete = taskRepository.findByIdAndUserIdAndDeletedFalse(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        taskToDelete.setDeleted(true);
        taskRepository.save(taskToDelete);
    }

    public TaskResponse markDone(@NotNull final UUID userId, @NotNull final UUID taskId) {
        Task completedTask = taskRepository.findByIdAndUserIdAndDeletedFalse(taskId, userId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        completedTask.setStatus(TaskStatus.DONE);
        completedTask.setCompletedAt(now());
        taskRepository.save(completedTask);
        return taskConverter.modelToResponse(completedTask);
    }


}
