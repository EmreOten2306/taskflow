INSERT INTO label (created_at, updated_at, name)
VALUES
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Backend'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Frontend'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Bug'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Feature'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Urgent'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Testing'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Database'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Security'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'Documentation'),
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'UI/UX')
ON CONFLICT (name) DO NOTHING;

-- PROJECTS
INSERT INTO project (created_at, updated_at, code, description, name, status, owner_id)
VALUES
    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'TF001',
     'Task management application',
     'TaskFlow Development',
     'ACTIVE', 1),

    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'TF002',
     'Online shopping platform',
     'E-Commerce Platform',
     'ACTIVE', 2),

    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'TF003',
     'Mobile banking application',
     'Mobile Banking App',
     'ACTIVE', 1),

    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'TF004',
     'Social media application',
     'Social Media Platform',
     'ACTIVE', 2),

    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'TF005',
     'Food ordering and delivery system',
     'Food Delivery System',
     'ACTIVE', 1),

    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'TF006',
     'Hospital management system',
     'Hospital Management',
     'ACTIVE', 2),

    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'TF007',
     'Online learning platform',
     'Online Education Platform',
     'ACTIVE', 1),

    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'TF008',
     'Stock and inventory management system',
     'Inventory Management',
     'ACTIVE', 2),

    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'TF009',
     'Event planning and management system',
     'Event Management',
     'ARCHIVED', 1),

    (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'TF010',
     'Hotel and flight booking platform',
     'Travel Booking System',
     'ARCHIVED', 2)

ON CONFLICT (code) DO NOTHING;