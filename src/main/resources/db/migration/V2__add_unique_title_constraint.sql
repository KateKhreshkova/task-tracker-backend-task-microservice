ALTER TABLE tasks
    ADD CONSTRAINT uk_tasks_title UNIQUE (title);
