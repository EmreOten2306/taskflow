INSERT INTO app_user (full_name, email, password_hash, role)
VALUES ('Admin User',
        'admin@taskflow.local',
        '$2a$10$sr3nPaI4.syPlnF7Sm.81OnKO9umWYAg.X6c.pZn549.hAXpSoVkq',
        'ADMIN')
ON CONFLICT DO NOTHING;

INSERT INTO app_user (full_name, email, password_hash, role)
VALUES ('Member User',
        'member@taskflow.local',
        '$2a$10$0FhUAgOwP7/ds2q.Csp5BuAP1pGp2UE9p.2BKSnawk40IHQmOpHS2',
        'MEMBER')
ON CONFLICT DO NOTHING;