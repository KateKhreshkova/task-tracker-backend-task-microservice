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
    public List<TaskResponse> list(@RequestHeader("X-User-Id") @NotNull final UUID userId){
        return null;
    }

    @PostMapping
    public TaskResponse create(@RequestHeader("X-User-Id") @NotNull final UUID userId,
                               @RequestBody @NotNull final TaskRequest taskRequest){
        return null;
    }

    @PutMapping("/{taskId}")
    public TaskResponse update(@RequestHeader("X-User-Id") @NotNull final UUID userId,
                               @PathVariable @NotNull final UUID taskId,
                               @RequestBody @NotNull final TaskRequest taskRequest){
        return null;
    }

    @DeleteMapping("/{taskId}")
    public void delete(@RequestHeader("X-User-Id") @NotNull final UUID userId,
                       @PathVariable @NotNull final UUID taskId){
    }

    @PutMapping("/done/{taskId}")
    public TaskResponse done(@RequestHeader("X-User-Id") @NotNull final UUID userId,
                             @PathVariable @NotNull final UUID taskId) {
        return null;
    }
}
