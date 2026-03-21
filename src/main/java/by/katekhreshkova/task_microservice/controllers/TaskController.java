package by.katekhreshkova.task_microservice.controllers;

import by.katekhreshkova.task_microservice.contracts.req.TaskRequest;
import by.katekhreshkova.task_microservice.contracts.resp.TaskResponse;
import by.katekhreshkova.task_microservice.services.TaskService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/*
 * REST controller responsible for handling HTTP requests related to tasks.
 * It exposes endpoints for creating, updating, retrieving and deleting tasks.
 */
@RestController

/*
 * Lombok annotation that automatically creates a constructor
 * for all final fields (in this case TaskService).
 * This is used for dependency injection.
 */
@RequiredArgsConstructor

/*
 * Base URL for all endpoints in this controller.
 * All routes will start with /api/tasks
 */
@RequestMapping("/api/tasks")
public class TaskController {

    /*
     * Service layer that contains business logic for working with tasks.
     * Injected automatically via constructor (because of @RequiredArgsConstructor).
     */
    private final TaskService taskService;

    /*
     * GET /api/tasks
     * Returns a list of all tasks belonging to the user.
     * The user ID is taken from the request header "X-User-Id".
     */
    @GetMapping
    public List<TaskResponse> list(@RequestHeader("X-User-Id") @NotNull final UUID userId) {
        return taskService.findAll(userId);
    }

    /*
     * POST /api/tasks
     * Creates a new task for the user.
     * userId is taken from the request header.
     * Task data (title, description, etc.) is passed in the request body.
     */
    @PostMapping
    public TaskResponse create(@RequestHeader("X-User-Id") @NotNull final UUID userId,
                               @RequestBody @NotNull final TaskRequest taskRequest) {
        return taskService.createTask(userId, taskRequest);
    }

    /*
     * PUT /api/tasks/{taskId}
     * Updates an existing task.
     * taskId is taken from the URL path.
     * Updated task data is passed in the request body.
     */
    @PutMapping("/{taskId}")
    public TaskResponse update(@RequestHeader("X-User-Id") @NotNull final UUID userId,
                               @PathVariable @NotNull final UUID taskId,
                               @RequestBody @NotNull final TaskRequest taskRequest) {
        return taskService.update(userId, taskId, taskRequest);
    }

    /*
     * DELETE /api/tasks/{taskId}
     * Deletes a task belonging to the user.
     */
    @DeleteMapping("/{taskId}")
    public void delete(@RequestHeader("X-User-Id") @NotNull final UUID userId,
                       @PathVariable @NotNull final UUID taskId) {
        taskService.delete(userId, taskId);
    }

    /*
     * PUT /api/tasks/done/{taskId}
     * Marks a task as completed.
     */
    @PutMapping("/done/{taskId}")
    public TaskResponse done(@RequestHeader("X-User-Id") @NotNull final UUID userId,
                             @PathVariable @NotNull final UUID taskId) {
        return taskService.markDone(userId, taskId);
    }
}