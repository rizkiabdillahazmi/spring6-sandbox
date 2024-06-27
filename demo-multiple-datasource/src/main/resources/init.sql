CREATE DATABASE example;

\c example

CREATE TABLE account
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255)
);