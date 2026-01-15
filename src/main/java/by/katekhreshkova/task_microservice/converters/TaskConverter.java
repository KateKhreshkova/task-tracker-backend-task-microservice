package by.katekhreshkova.task_microservice.converters;

import by.katekhreshkova.task_microservice.contracts.resp.TaskResponse;
import by.katekhreshkova.task_microservice.models.Task;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

@Component
public class TaskConverter {
    public TaskResponse modelToContract(@NotNull final Task task){
        return new TaskResponse(
                task.getId(),
                task.getUserId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getCompletedAt());
    }
}
