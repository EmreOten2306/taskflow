WITH project_stats AS (
    SELECT
        project.id AS project_id,
        project.name AS project_name,
        COUNT(task.id) AS total_tasks,

        COUNT(
                CASE
                    WHEN task.status = 'DONE' THEN 1
                    END
        ) AS completed_tasks,

        SUM(
                CASE
                    WHEN task.due_date < CURRENT_DATE
                        AND task.status != 'DONE'
                            THEN 1
                    ELSE 0
                    END
        ) AS overdue_tasks,

        AVG(
                CASE
                    WHEN task.status = 'DONE'
                        THEN EXTRACT(
                                     EPOCH FROM (task.completed_at - task.created_at)
                             ) / 86400
                    END
        ) AS average_completion_days

    FROM project
             LEFT JOIN task
                       ON project.id = task.project_id

    GROUP BY
        project.id,
        project.name
)

SELECT
    project_name,

    ROUND(
            completed_tasks * 100.0
                / NULLIF(total_tasks, 0),
            2
    ) AS completion_percentage,

    ROUND(
            overdue_tasks * 100.0
                / NULLIF(total_tasks, 0),
            2
    ) AS overdue_percentage,

    ROUND(
            average_completion_days,
            2
    ) AS average_completion_days

FROM project_stats

ORDER BY project_name;