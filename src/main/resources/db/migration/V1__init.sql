CREATE TABLE users
(
      id CHAR(36) PRIMARY KEY,
      name VARCHAR(255) NOT NULL UNIQUE,
      first_name VARCHAR(255) NOT NULL,
      last_name VARCHAR(255) NOT NULL,
      middle_name VARCHAR(255) NOT NULL,
      email VARCHAR(255) NOT NULL,
      created_at timestamp NOT NULL DEFAULT now()
);


