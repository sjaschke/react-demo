DROP TABLE IF EXISTS client;
CREATE TABLE client (
    id bigint auto_increment PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255)
);

