package by.katekhreshkova.task_microservice.kafka.events;

import java.util.UUID;

public record TaskDailySummaryEvent(UUID userId,
                                    long completedToday,
                                    long pendingTasks) {
}
