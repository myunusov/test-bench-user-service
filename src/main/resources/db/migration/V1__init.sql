CREATE TABLE users
(
      id CHAR(36) PRIMARY KEY,
      name VARCHAR(255) NOT NULL UNIQUE,
      firstName VARCHAR(255) NOT NULL,
      lastName VARCHAR(255) NOT NULL,
      middleName VARCHAR(255) NOT NULL,
      email VARCHAR(255) NOT NULL,
      createdat timestamp NOT NULL DEFAULT now()
);


