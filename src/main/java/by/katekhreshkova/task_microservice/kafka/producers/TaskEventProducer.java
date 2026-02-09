package by.katekhreshkova.task_microservice.kafka.producers;

import by.katekhreshkova.task_microservice.kafka.events.TaskDailySummaryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "tasks-daily-summary-events-topic";

    public void sendDailySummary(TaskDailySummaryEvent event) {
        kafkaTemplate.send(
                TOPIC,
                event.userId().toString(),
                event
        );
    }
}
