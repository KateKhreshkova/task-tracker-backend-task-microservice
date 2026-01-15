package by.katekhreshkova.task_microservice.contracts.resp;

import by.katekhreshkova.task_microservice.models.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponse(UUID id,
                           UUID userId,
                           String title,
                           String description,
                           TaskStatus status,
                           LocalDateTime createdAt,
                           LocalDateTime updatedAt,
                           LocalDateTime completedAt) {
}
