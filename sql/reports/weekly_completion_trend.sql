SELECT
    weeks.week_start,
    COUNT(task.id) AS completed_count
FROM generate_series(
             date_trunc('week', CURRENT_DATE) - interval '7 weeks',
             date_trunc('week', CURRENT_DATE),
             interval '1 week'
     ) AS weeks(week_start)
         LEFT JOIN task
                   ON weeks.week_start = date_trunc('week', task.completed_at)
                       AND task.status = 'DONE'
GROUP BY weeks.week_start
ORDER BY weeks.week_start;
