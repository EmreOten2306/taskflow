SELECT
    COUNT(*) FILTER (WHERE status = 'TODO') AS todo_count,
    COUNT(*) FILTER (WHERE status = 'IN_PROGRESS') AS in_progress_count,
    COUNT(*) FILTER (WHERE status = 'IN_REVIEW') AS in_review_count,
    COUNT(*) FILTER (WHERE status = 'DONE') AS done_count
FROM task
WHERE project_id = ?