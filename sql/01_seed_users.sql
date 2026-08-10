INSERT INTO app_user (full_name, email, password_hash, role)
VALUES ('Admin User',
        'admin@taskflow.local',
        '$2a$10$OGDeCybfD0RKlAkCnLWsO.FafD5ns9Uh8miwzzXJOznzEl1SSJwu6',
        'ADMIN')
ON CONFLICT DO NOTHING;

INSERT INTO app_user (full_name, email, password_hash, role)
VALUES ('Member User',
        'member@taskflow.local',
        '$2a$10$Aj8wYErgacHqbhoLchzB1O3YJnBaQ8RGSyIUEiwOHIkF10LG59glm',
        'MEMBER')
ON CONFLICT DO NOTHING;