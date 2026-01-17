package by.katekhreshkova.task_microservice.controllers;

import by.katekhreshkova.task_microservice.contracts.req.TaskRequest;
import by.katekhreshkova.task_microservice.contracts.resp.TaskResponse;
import by.katekhreshkova.task_microservice.services.TaskService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<TaskResponse> list(@RequestHeader("X-User-Id") @NotNull final UUID userId) {
        return taskService.findAll(userId);
    }

    @PostMapping
    public TaskResponse create(@RequestHeader("X-User-Id") @NotNull final UUID userId,
                               @RequestBody @NotNull final TaskRequest taskRequest) {
        return taskService.createTask(userId, taskRequest);
    }

    @PutMapping("/{taskId}")
    public TaskResponse update(@RequestHeader("X-User-Id") @NotNull final UUID userId,
                               @PathVariable @NotNull final UUID taskId,
                               @RequestBody @NotNull final TaskRequest taskRequest) {
        return taskService.update(userId, taskId, taskRequest);
    }

    @DeleteMapping("/{taskId}")
    public void delete(@RequestHeader("X-User-Id") @NotNull final UUID userId,
                       @PathVariable @NotNull final UUID taskId) {
        taskService.delete(userId, taskId);
    }

    @PutMapping("/done/{taskId}")
    public TaskResponse done(@RequestHeader("X-User-Id") @NotNull final UUID userId,
                             @PathVariable @NotNull final UUID taskId) {
        return taskService.markDone(userId, taskId);
    }
}
