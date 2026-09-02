INSERT INTO task (
                  created_at,
                  updated_at,
                  description,
                  due_date,
                  priority,
                  status,
                  title,
                  assignee_id,
                  project_id,
                  completed_at
)
SELECT
    now() - (i || ' minutes')::interval,
       now(),
       'Performance test task',
       CURRENT_DATE + ((i % 120) - 60),
       (ARRAY['LOW','MEDIUM','HIGH','CRITICAL'])[1 + (i % 4)],
       (ARRAY['TODO','IN_PROGRESS','IN_REVIEW','DONE'])[1 + (i % 4)],
       'Task #' || i,
    CASE
        WHEN i % 7 = 0 THEN NULL
        ELSE (ARRAY[1,2,4])[1 + (i % 3)]
        END,
    (ARRAY[29,30,31,33,34,35,36,37,38])[1 + (i % 9)],
       CASE WHEN i % 4 = 1
         THEN now() - ((i % 56) || ' days')::interval
            ELSE NULL END
FROM generate_series(1, 50000) AS s(i);

INSERT INTO comment (
    created_at,
    updated_at,
    content,
    author_id,
    task_id
)
SELECT
    now(),
    now(),
    'Performance test comment',
    2,
    100013 + ((i - 1) % 50000)
FROM generate_series(1, 100000) AS s(i);

EXPLAIN (ANALYZE, BUFFERS)
SELECT
    id,
    title,
    due_Date,
    CURRENT_DATE - due_date::date AS delay_days
FROM task
WHERE due_date < CURRENT_DATE
  AND status != 'DONE';
