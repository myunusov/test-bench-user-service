CREATE TABLE departments
(
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    manager_id CHAR(36) NOT NULL,
    FOREIGN KEY (manager_id)
        REFERENCES users(id)
        ON DELETE RESTRICT
);

ALTER TABLE users
    ADD department_id CHAR(36);