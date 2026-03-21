package by.katekhreshkova.task_microservice.repositories;

import by.katekhreshkova.task_microservice.models.Task;
import by.katekhreshkova.task_microservice.models.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
 * Repository interface for performing database operations
 * on Task entities.
 *
 * JpaRepository provides built-in CRUD operations:
 * save(), findById(), delete(), findAll(), etc.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    /*
     * Finds all tasks for a specific user that are not deleted.
     * Uses Spring Data JPA method naming convention.
     */
    List<Task> findAllByUserIdAndDeletedFalse(UUID userId);

    /*
     * Custom JPQL query that finds a task by:
     * - task id
     * - user id
     * - ensuring the task is not marked as deleted
     *
     * Returns Optional because the task may not exist.
     */
    @Query("""
                select t from Task t
                where t.id = :id
                  and t.userId = :userId
                  and t.deleted = false
            """)
    Optional<Task> findByIdAndUserIdAndDeletedFalse(UUID id, UUID userId);

    /*
     * Returns all unique user IDs that currently have tasks.
     * Only tasks that are not deleted are considered.
     */
    @Query("SELECT DISTINCT t.userId FROM Task t WHERE t.deleted = false")
    List<UUID> findAllUserIds();

    /*
     * Counts how many tasks a user completed on a specific date.
     *
     * Conditions:
     * - task belongs to the user
     * - task status is DONE
     * - completion date matches the provided date
     */
    @Query("""
    SELECT COUNT(t) FROM Task t
    WHERE t.userId = :userId
      AND t.status = 'DONE'
      AND DATE(t.completedAt) = :date
""")
    long countCompletedToday(UUID userId, LocalDate date);

    /*
     * Counts tasks for a user with a specific status
     * (for example PENDING or DONE).
     *
     * Uses Spring Data JPA derived query method.
     */
    long countByUserIdAndStatus(UUID userId, TaskStatus status);

}
