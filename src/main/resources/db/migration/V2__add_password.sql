-- src/main/resources/db/migration/V2__add_password.sql

-- Add password column to employees table
ALTER TABLE employees ADD COLUMN password VARCHAR(255);

-- Update admin user with a default password (password: "admin")
UPDATE employees
SET password = '$2a$10$Jg6SNf1oAZF0lvI2D1EfouL5bbzC0K8EV9M4oZa5fUbXQ3/R9/TIu'
WHERE name = 'admin';