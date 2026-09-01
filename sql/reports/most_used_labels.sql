SELECT
    label.name,
    COUNT(task_label.tasks_id) AS usage_count
FROM label
         JOIN task_label
              ON label.id = task_label.labels_id
GROUP BY label.name
ORDER BY usage_count DESC
    LIMIT 10;