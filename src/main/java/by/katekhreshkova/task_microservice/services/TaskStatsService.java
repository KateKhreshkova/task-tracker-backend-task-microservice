package by.katekhreshkova.task_microservice.services;

import by.katekhreshkova.task_microservice.kafka.events.TaskDailySummaryEvent;
import by.katekhreshkova.task_microservice.models.TaskStatus;
import by.katekhreshkova.task_microservice.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class TaskStatsService {
    private final TaskRepository taskRepository;

    public List<TaskDailySummaryEvent> getDailyStats() {
        LocalDate today = LocalDate.now();

        List<UUID> users = taskRepository.findAllUserIds();

        return users.stream().map(userId -> {
            long completedToday = taskRepository.countCompletedToday(userId, today);
            long pending = taskRepository.countByUserIdAndStatus(userId, TaskStatus.PENDING);

            return new TaskDailySummaryEvent(userId, completedToday, pending);
        }).toList();
    }
}
