CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TABLE IF NOT EXISTS books
(
    id          UUID DEFAULT uuid_generate_v4(),
    isbn        VARCHAR(255),
    title       VARCHAR(255),
    description VARCHAR(255),
    tags        VARCHAR(255)[],
    metadata    JSON DEFAULT '{}',
    created_at  TIMESTAMP, --NOT NULL DEFAULT LOCALTIMESTAMP,
    updated_at  TIMESTAMP,
    version     INTEGER
);
