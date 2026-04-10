-- Drop the old unique constraint on title only (if it exists)
ALTER TABLE tasks DROP CONSTRAINT IF EXISTS uk_tasks_title;

-- Drop the unique index on title only (if it exists)
DROP INDEX IF EXISTS uk_tasks_title;

-- Create a composite unique constraint on user_id and title
-- This allows different users to have tasks with the same title
-- but prevents the same user from having duplicate task titles
ALTER TABLE tasks ADD CONSTRAINT uk_tasks_user_id_title UNIQUE (user_id, title);