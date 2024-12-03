-- Create 'roles' table
CREATE TABLE roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(255) UNIQUE NOT NULL
);

-- Create 'users' table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Create 'user_roles' table
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

-- Optional: Create 'role_authorities' table for storing permissions
CREATE TABLE role_authorities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_id BIGINT NOT NULL,
    authority VARCHAR(255) NOT NULL,
    CONSTRAINT fk_role_authority FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

-- Insert initial data into 'roles' table
INSERT INTO roles (role_name) VALUES ('ROLE_USER');
INSERT INTO roles (role_name) VALUES ('ROLE_ADMIN');

-- Insert initial data into 'users' table
INSERT INTO users (username, password) VALUES ('john_doe', 'password123');
INSERT INTO users (username, password) VALUES ('admin_user', 'adminpass');

-- Insert initial data into 'user_roles' table
INSERT INTO user_roles (user_id, role_id)
VALUES 
    ((SELECT id FROM users WHERE username = 'john_doe'), (SELECT id FROM roles WHERE role_name = 'ROLE_USER')),
    ((SELECT id FROM users WHERE username = 'admin_user'), (SELECT id FROM roles WHERE role_name = 'ROLE_ADMIN'));

-- Optional: Insert initial data into 'role_authorities' table
INSERT INTO role_authorities (role_id, authority)
VALUES 
    ((SELECT id FROM roles WHERE role_name = 'ROLE_USER'), 'READ_PRIVILEGES'),
    ((SELECT id FROM roles WHERE role_name = 'ROLE_USER'), 'WRITE_PRIVILEGES'),
    ((SELECT id FROM roles WHERE role_name = 'ROLE_ADMIN'), 'READ_PRIVILEGES'),
    ((SELECT id FROM roles WHERE role_name = 'ROLE_ADMIN'), 'WRITE_PRIVILEGES'),
    ((SELECT id FROM roles WHERE role_name = 'ROLE_ADMIN'), 'DELETE_PRIVILEGES');
