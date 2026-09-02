CREATE INDEX IF NOT EXISTS idx_task_status
    ON task (status);

CREATE INDEX IF NOT EXISTS idx_task_assignee_id
    ON task (assignee_id);

CREATE INDEX IF NOT EXISTS idx_task_due_date
    ON task (due_date);

CREATE INDEX IF NOT EXISTS idx_task_project_status
    ON task (project_id, status);

CREATE INDEX IF NOT EXISTS idx_comment_task_id
    ON comment (task_id);

ANALYZE task;