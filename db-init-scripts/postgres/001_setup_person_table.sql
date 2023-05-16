CREATE TABLE people
(
    id         serial PRIMARY KEY,
    ref        VARCHAR(50) UNIQUE NOT NULL,
    first_name VARCHAR(50)        NOT NULL,
    last_name  VARCHAR(50) UNIQUE NOT NULL,
    birth_date VARCHAR(10)
);

INSERT INTO people (ref, first_name, last_name, birth_date)
VALUES ('13ed6a67-a4c4-4307-85da-2accbcf25aa7', 'Maarten', 'Vandeperre', '17/04/1989');