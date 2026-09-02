SELECT
    app_user.full_name,
    SUM(CASE
            WHEN priority = 'HIGH' THEN 1
            ELSE 0
        END
    ) AS high_count,
    SUM(CASE
            WHEN priority = 'MEDIUM' THEN 1
            ELSE 0
        END
    ) AS medium_count,
    SUM(CASE
            WHEN priority = 'LOW' THEN 1
            ELSE 0
        END
    ) AS low_count
FROM app_user
         LEFT JOIN task
                   ON app_user.id = task.assignee_id
                       AND status IN('TODO','IN_PROGRESS','IN_REVIEW')
GROUP BY app_user.full_name;