package by.katekhreshkova.task_microservice.kafka.schedulers;

import by.katekhreshkova.task_microservice.kafka.producers.TaskEventProducer;
import by.katekhreshkova.task_microservice.services.TaskStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyTasksScheduler {
    private final TaskStatsService statsService;
    private final TaskEventProducer producer;

    @Scheduled(cron = "0 0 0 * * *", zone = "UTC")
    public void sendDailySummary() {
        statsService.getDailyStats()
                .forEach(producer::sendDailySummary);
    }
}
