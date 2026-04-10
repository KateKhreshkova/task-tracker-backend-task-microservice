package by.katekhreshkova.task_microservice.services;

import by.katekhreshkova.task_microservice.contracts.req.TaskRequest;
import by.katekhreshkova.task_microservice.contracts.resp.TaskResponse;
import by.katekhreshkova.task_microservice.converters.TaskConverter;
import by.katekhreshkova.task_microservice.models.Task;
import by.katekhreshkova.task_microservice.models.TaskStatus;
import by.katekhreshkova.task_microservice.repositories.TaskRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.UUID;

import static java.time.LocalDateTime.*;

/*
 * Service layer responsible for business logic related to tasks.
 * Handles creation, retrieval, update, deletion, and completion of tasks.
 */
@Service

/*
 * Lombok annotation that generates a constructor
 * for all final fields (used for dependency injection).
 */
@RequiredArgsConstructor
public class TaskService {

    /*
     * Repository used for database operations with Task entities.
     */
    private final TaskRepository taskRepository;

    /*
     * Converter used to transform Task model objects
     * into TaskResponse DTOs for API responses.
     */
    private final TaskConverter taskConverter;

    /*
     * Creates a new task for the specified user.
     */
    public TaskResponse createTask(@NotNull final UUID userId, @NotNull final TaskRequest taskRequest){

        // Create a new Task entity
        Task taskToSave = new Task();

        // Set task owner
        taskToSave.setUserId(userId);

        // Set task properties from request
        taskToSave.setTitle(taskRequest.title());
        taskToSave.setDescription(taskRequest.description());

        // New tasks start with PENDING status
        taskToSave.setStatus(TaskStatus.PENDING);

        // Set creation timestamp
        taskToSave.setCreatedAt(now());

        // Save task to database and convert it to response DTO
        try {
            return taskConverter.modelToResponse(taskRepository.save(taskToSave));
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "You already have a task with this title",
                    ex
            );
        }
    }

    /*
     * Retrieves all non-deleted tasks belonging to a specific user.
     */
    public List<TaskResponse> findAll(@NotNull final UUID userId) {

        return taskRepository.findAllByUserIdAndDeletedFalse(userId)
                .stream() // convert list to stream
                .map(taskConverter::modelToResponse) // convert each Task -> TaskResponse
                .toList(); // return as List
    }

    /*
     * Updates the title and description of an existing task.
     */
    public TaskResponse update(@NotNull final UUID userId, @NotNull final UUID taskId, @NotNull final TaskRequest req) {

        // Find task by id and userId, ensure it is not deleted
        Task taskToUpdate = taskRepository.findByIdAndUserIdAndDeletedFalse(taskId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        // Update task fields
        taskToUpdate.setTitle(req.title());
        taskToUpdate.setDescription(req.description());

        // Update modification timestamp
        taskToUpdate.setUpdatedAt(now());

        // Save updated task
        try {
            taskRepository.save(taskToUpdate);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "You already have a task with this title",
                    ex
            );
        }

        // Convert updated task to response DTO
        return taskConverter.modelToResponse(taskToUpdate);
    }

    /*
     * Soft deletes a task (marks it as deleted instead of removing it from database).
     */
    public void delete(@NotNull final UUID userId, @NotNull final UUID taskId) {

        // Find task belonging to the user
        Task taskToDelete = taskRepository.findByIdAndUserIdAndDeletedFalse(taskId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        // Mark task as deleted
        taskToDelete.setDeleted(true);

        // Save updated state
        taskRepository.save(taskToDelete);
    }

    /*
     * Marks a task as completed.
     */
    public TaskResponse markDone(@NotNull final UUID userId, @NotNull final UUID taskId) {

        // Find the task belonging to the user
        Task completedTask = taskRepository.findByIdAndUserIdAndDeletedFalse(taskId, userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        // Change status to DONE
        completedTask.setStatus(TaskStatus.DONE);

        // Record completion timestamp
        completedTask.setCompletedAt(now());

        // Save updated task
        taskRepository.save(completedTask);

        // Convert to response DTO
        return taskConverter.modelToResponse(completedTask);
    }
}
