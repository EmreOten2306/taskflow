SELECT
    id,
    title,
    due_Date,
    CURRENT_DATE - due_date::date AS delay_days
FROM task
WHERE due_date <CURRENT_DATE
  AND status != 'DONE'