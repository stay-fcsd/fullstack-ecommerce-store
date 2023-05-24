CREATE TABLE customers(
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(30) NOT NULL,
    email VARCHAR(30) UNIQUE NOT NULL,
    password VARCHAR NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT FALSE,
    PRIMARY KEY (id)
);