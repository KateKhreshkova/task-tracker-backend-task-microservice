CREATE TABLE tasks
(
    id           UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    user_id      UUID         NOT NULL,
    title        VARCHAR(255) NOT NULL,
    description  TEXT,
    status       VARCHAR(50)  NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP WITHOUT TIME ZONE,
    completed_at TIMESTAMP WITHOUT TIME ZONE,
    deleted      BOOLEAN      NOT NULL DEFAULT FALSE
);
CREATE INDEX idx_tasks_user_id ON tasks (user_id);
CREATE INDEX idx_tasks_status ON tasks (status);
CREATE INDEX idx_tasks_deleted ON tasks (deleted);