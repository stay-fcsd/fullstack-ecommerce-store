CREATE TABLE categories(
    id INTEGER GENERATED ALWAYS AS IDENTITY,
    category VARCHAR(50) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

INSERT INTO categories(category)
VALUES
('EDUCATION'),
('ELECTRONICS'),
('SPORTS'),
('FASHION');
