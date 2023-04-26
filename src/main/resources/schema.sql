CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS person
(
    email     VARCHAR(255) PRIMARY KEY,
    location  VARCHAR(255),
    avatar    VARCHAR(255),
    version   INTEGER
);
