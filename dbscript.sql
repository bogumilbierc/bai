
-- ----------------------------------------------------------------------
-- ------- DROP--------------------------------------------------------
-- ----------------------------------------------------------------------

DROP SCHEMA `bai`;

-- ----------------------------------------------------------------------
-- ------- SCHEMA--------------------------------------------------------
-- ----------------------------------------------------------------------

CREATE SCHEMA `bai`
  DEFAULT CHARACTER SET utf8;

USE bai;

-- ----------------------------------------------------------------------
-- ------- TABLES--------------------------------------------------------
-- ----------------------------------------------------------------------

CREATE TABLE USER_PROFILE (
  id                             INTEGER NOT NULL AUTO_INCREMENT,
  login                          VARCHAR(255) UNIQUE,
  last_login_date                DATETIME,
  LAST_FAILED_LOGIN_DATE         DATETIME,
  BLOCKADE_DEADLINE              DATETIME,
  PASSWORD                       VARCHAR(255),
  IS_ACTIVE                      BOOLEAN,
  NO_OF_FAILED_LOGINS_SINCE_LAST INTEGER,
  NO_OF_ATTEMPTS_BEFORE_BLOCKADE INTEGER,
  DELAY_IN_SECONDS               INTEGER,
  PRIMARY KEY (id)
);

CREATE TABLE NOT_EXISTING_USER_PROFILE (
  id                             INTEGER NOT NULL AUTO_INCREMENT,
  login                          VARCHAR(255) UNIQUE,
  LAST_FAILED_LOGIN_DATE         DATETIME,
  BLOCKADE_DEADLINE              DATETIME,
  IS_ACTIVE                      BOOLEAN,
  NO_OF_FAILED_LOGINS_SINCE_LAST INTEGER,
  NO_OF_ATTEMPTS_BEFORE_BLOCKADE INTEGER,
  DELAY_IN_SECONDS               INTEGER,
  PRIMARY KEY (id)
);

CREATE TABLE MESSAGE (
  id       INTEGER NOT NULL AUTO_INCREMENT,
  CONTENT  VARCHAR(555),
  OWNER_ID INTEGER,
  PRIMARY KEY (id)
);

ALTER TABLE MESSAGE ADD CONSTRAINT messageOwnerId FOREIGN KEY (OWNER_ID) REFERENCES USER_PROFILE (id);

CREATE TABLE ALLOWED_MESSAGE (
  id         INTEGER NOT NULL AUTO_INCREMENT,
  USER_ID    INTEGER,
  MESSAGE_ID INTEGER,
  PRIMARY KEY (id)
);

-- ----------------------------------------------------------------------
-- ------- DATA ---------------------------------------------------------
-- ----------------------------------------------------------------------

INSERT INTO USER_PROFILE (ID, login, password, IS_ACTIVE, NO_OF_ATTEMPTS_BEFORE_BLOCKADE, DELAY_IN_SECONDS)
VALUES (1, 'bolek', '$2a$10$GdvBEHy0OIGdH1lYWrTKb.3gQd8SBRKM7Apo8IgcAsp2FJKT1hoFu', TRUE, 3, 4); -- Bolek123!
INSERT INTO USER_PROFILE (ID, login, password, IS_ACTIVE, NO_OF_ATTEMPTS_BEFORE_BLOCKADE, DELAY_IN_SECONDS)
VALUES (2, 'lolek', '$2a$10$FG.2sVbLkxD8QVHv8B.sPewZvAKz.gSEyQ83qwO0qw9ECfFfWN/JC', TRUE, 3, 5); -- Lolek123!
INSERT INTO USER_PROFILE (ID, login, password, IS_ACTIVE, NO_OF_ATTEMPTS_BEFORE_BLOCKADE, DELAY_IN_SECONDS)
VALUES (3, 'antek', '$2a$10$XxKxThU2TzKXk2xvA8xOIerOOW/WDjGvLXCZYIDYAKEYFSA0yEOu6', TRUE, 3, 2); -- Antek123!

INSERT INTO MESSAGE (ID, CONTENT, OWNER_ID) VALUES (1, 'Wsiada baba do windy a tam schody', 1);
INSERT INTO MESSAGE (ID, CONTENT, OWNER_ID) VALUES (2, 'Wchodzi popek do salonu Ery i prosi telefon na firme', 1);
INSERT INTO MESSAGE (ID, CONTENT, OWNER_ID) VALUES (3, 'Dlaczego murzyn nie może być żydem? Bo bez przesady.', 2);
INSERT INTO MESSAGE (ID, CONTENT, OWNER_ID) VALUES (4, '0.7 zgłoś się', 2);
INSERT INTO MESSAGE (ID, CONTENT, OWNER_ID) VALUES (5, 'pehap pehap pehap pfffffff', 2);

INSERT INTO ALLOWED_MESSAGE (USER_ID, MESSAGE_ID) VALUES (1, 3);
INSERT INTO ALLOWED_MESSAGE (USER_ID, MESSAGE_ID) VALUES (3, 1);
INSERT INTO ALLOWED_MESSAGE (USER_ID, MESSAGE_ID) VALUES (3, 2);
INSERT INTO ALLOWED_MESSAGE (USER_ID, MESSAGE_ID) VALUES (3, 5);
