DROP TABLE todo IF EXISTS;
DROP TABLE user IF EXISTS;

CREATE TABLE todo (
  id            BIGINT IDENTITY PRIMARY KEY,
  user_id       BIGINT,
  description   text,
  completed_at  DATETIME,
  updated_at    DATETIME,
  created_at    DATETIME
);

CREATE TABLE user (
  id            BIGINT IDENTITY PRIMARY KEY,
  username      VARCHAR(80),
  password      VARCHAR(80),
  updated_at    DATETIME,
  created_at    DATETIME
);
