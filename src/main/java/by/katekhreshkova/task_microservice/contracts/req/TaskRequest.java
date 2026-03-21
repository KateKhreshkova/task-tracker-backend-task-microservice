package by.katekhreshkova.task_microservice.contracts.req;


/*
 * Data Transfer Object (DTO) used to receive task data
 * from the client in HTTP requests.
 *
 * This object is typically sent in the request body
 * when creating or updating a task.
 *
 * Java record is used to create an immutable object
 * with automatically generated constructor, getters,
 * equals(), hashCode(), and toString().
 */
public record TaskRequest(

        // Title of the task provided by the user
        String title,

        // Description or details of the task
        String description

) {
}