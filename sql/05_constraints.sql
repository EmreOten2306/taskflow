-- Eski Hibernate CHECK constraint'lerini kaldır
ALTER TABLE task
DROP CONSTRAINT IF EXISTS task_status_check;

ALTER TABLE task
DROP CONSTRAINT IF EXISTS task_priority_check;

-- Script daha önce çalıştırıldıysa eski constraint'leri kaldır
ALTER TABLE task
DROP CONSTRAINT IF EXISTS uk_task_project_title;

ALTER TABLE task
DROP CONSTRAINT IF EXISTS ck_task_status;

ALTER TABLE task
DROP CONSTRAINT IF EXISTS ck_task_priority;

-- Aynı project içinde aynı task title tekrar edemez
ALTER TABLE task
    ADD CONSTRAINT uk_task_project_title UNIQUE (project_id, title);

-- Status
ALTER TABLE task
    ADD CONSTRAINT ck_task_status
        CHECK (status IN ('TODO', 'IN_PROGRESS', 'IN_REVIEW', 'DONE'));

-- Priority
ALTER TABLE task
    ADD CONSTRAINT ck_task_priority
        CHECK (priority IN ('LOW', 'MEDIUM', 'HIGH', 'CRITICAL'));