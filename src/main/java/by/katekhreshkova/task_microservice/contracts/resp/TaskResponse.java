package by.katekhreshkova.task_microservice.contracts.resp;

import by.katekhreshkova.task_microservice.models.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

/*
 * Data Transfer Object (DTO) used to send task data
 * from the backend service to the client (API response).
 *
 * Java record is used because it is immutable and
 * automatically generates constructor, getters,
 * equals(), hashCode(), and toString().
 */
public record TaskResponse(

        // Unique identifier of the task
        UUID id,

        // ID of the user who owns the task
        UUID userId,

        // Title of the task
        String title,

        // Detailed description of the task
        String description,

        // Current status of the task (e.g., PENDING, DONE)
        TaskStatus status,

        // Timestamp when the task was created
        LocalDateTime createdAt,

        // Timestamp when the task was last updated
        LocalDateTime updatedAt,

        // Timestamp when the task was completed
        LocalDateTime completedAt

) {
}