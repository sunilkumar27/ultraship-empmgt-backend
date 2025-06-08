-- src/main/resources/db/migration/V1__init.sql

-- Create employees table
CREATE TABLE employees (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INTEGER NOT NULL,
    class_name VARCHAR(255),
    role VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Create subjects table
CREATE TABLE subjects (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Create employee_subjects join table
CREATE TABLE employee_subjects (
    employee_id VARCHAR(36) NOT NULL,
    subject_id VARCHAR(36) NOT NULL,
    PRIMARY KEY (employee_id, subject_id),
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subjects(id) ON DELETE CASCADE
);

-- Create attendance table
CREATE TABLE attendance (
    id VARCHAR(36) PRIMARY KEY,
    employee_id VARCHAR(36) NOT NULL,
    date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE
);

-- Create indexes
CREATE INDEX idx_employees_name ON employees(name);
CREATE INDEX idx_employees_role ON employees(role);
CREATE INDEX idx_attendance_date ON attendance(date);
CREATE INDEX idx_attendance_employee_id ON attendance(employee_id);

-- Insert admin user
INSERT INTO employees (id, name, age, class_name, role, created_at, updated_at)
VALUES (
    gen_random_uuid(), 
    'admin',
    30,
    'Admin Class',
    'ADMIN',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
);

-- Insert some sample subjects
INSERT INTO subjects (id, name, created_at, updated_at)
VALUES 
    (gen_random_uuid(), 'Mathematics', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'Physics', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'Chemistry', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (gen_random_uuid(), 'Biology', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
    
-- Insert employee-subject associations for admin
INSERT INTO employee_subjects (employee_id, subject_id)
SELECT e.id, s.id
FROM employees e, subjects s
WHERE e.name = 'admin' AND s.name = 'Mathematics';

INSERT INTO employee_subjects (employee_id, subject_id)
SELECT e.id, s.id
FROM employees e, subjects s
WHERE e.name = 'admin' AND s.name = 'Physics';

-- Insert attendance records for admin (last 5 days)
INSERT INTO attendance (id, employee_id, date, status, created_at, updated_at)
SELECT 
    gen_random_uuid(),
    e.id,
    CURRENT_DATE - INTERVAL '4 days',
    'PRESENT',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM employees e
WHERE e.name = 'admin';

INSERT INTO attendance (id, employee_id, date, status, created_at, updated_at)
SELECT 
    gen_random_uuid(),
    e.id,
    CURRENT_DATE - INTERVAL '3 days',
    'PRESENT',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM employees e
WHERE e.name = 'admin';

INSERT INTO attendance (id, employee_id, date, status, created_at, updated_at)
SELECT 
    gen_random_uuid(),
    e.id,
    CURRENT_DATE - INTERVAL '2 days',
    'LATE',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM employees e
WHERE e.name = 'admin';

INSERT INTO attendance (id, employee_id, date, status, created_at, updated_at)
SELECT 
    gen_random_uuid(),
    e.id,
    CURRENT_DATE - INTERVAL '1 day',
    'PRESENT',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM employees e
WHERE e.name = 'admin';

INSERT INTO attendance (id, employee_id, date, status, created_at, updated_at)
SELECT 
    gen_random_uuid(),
    e.id,
    CURRENT_DATE,
    'PRESENT',
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP
FROM employees e
WHERE e.name = 'admin';    