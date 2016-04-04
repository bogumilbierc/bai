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
  CURRENT_PASSWORD_FRAGMENT_ID   INTEGER,
  PASSWORD_LENGTH                INTEGER,
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

CREATE TABLE PASSWORD_FRAGMENT (
  id            INTEGER NOT NULL AUTO_INCREMENT,
  USER_ID       INTEGER,
  JSON_MASK     VARCHAR(255),
  PASSWORD_HASH VARCHAR(255),
  PRIMARY KEY (id)
);

-- ----------------------------------------------------------------------
-- ------- DATA ---------------------------------------------------------
-- ----------------------------------------------------------------------

-- USER_PROFILE

INSERT INTO `user_profile` (`id`, `login`, `last_login_date`, `LAST_FAILED_LOGIN_DATE`, `BLOCKADE_DEADLINE`, `PASSWORD`, `IS_ACTIVE`, `NO_OF_FAILED_LOGINS_SINCE_LAST`, `NO_OF_ATTEMPTS_BEFORE_BLOCKADE`, `DELAY_IN_SECONDS`, `CURRENT_PASSWORD_FRAGMENT_ID`, `PASSWORD_LENGTH`)
VALUES (1, 'bolek', NULL, NULL, NULL, '$2a$10$rYFAEoAqxAyJtyI0pJpOqORQugqBhVTseQLCZsxCWdWz2HQAanjiG', 1, 0, 3, 3, 1,
        9); -- Bolek123!
INSERT INTO `user_profile` (`id`, `login`, `last_login_date`, `LAST_FAILED_LOGIN_DATE`, `BLOCKADE_DEADLINE`, `PASSWORD`, `IS_ACTIVE`, `NO_OF_FAILED_LOGINS_SINCE_LAST`, `NO_OF_ATTEMPTS_BEFORE_BLOCKADE`, `DELAY_IN_SECONDS`, `CURRENT_PASSWORD_FRAGMENT_ID`, `PASSWORD_LENGTH`)
VALUES (2, 'lolek', NULL, NULL, NULL, '$2a$10$W5XSDb6lmNGiWwLGvNb1KuKvvkg0OPA5MAWYBuUJlWZ/f9212x5P2', 1, 0, 3, 3, 11,
        9); -- Lolek123!
INSERT INTO `user_profile` (`id`, `login`, `last_login_date`, `LAST_FAILED_LOGIN_DATE`, `BLOCKADE_DEADLINE`, `PASSWORD`, `IS_ACTIVE`, `NO_OF_FAILED_LOGINS_SINCE_LAST`, `NO_OF_ATTEMPTS_BEFORE_BLOCKADE`, `DELAY_IN_SECONDS`, `CURRENT_PASSWORD_FRAGMENT_ID`, `PASSWORD_LENGTH`)
VALUES (3, 'antek', NULL, NULL, NULL, '$2a$10$W/8l/k2HwIJeVVkunFuTfuvEPbMgWikLlT/ugTmpTS.t65yEbIJru', 1, 0, 3, 3, 21,
        9); -- Antek123!

-- PASSWORD_FRAGMENT

INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (1, 1, '[0,1,2,6,7]', '$2a$10$bKNSOD7Frb74aM3LFao9ueXmhvmpW6Bp2Rxq.TJMhStjldzaFqrfC');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (2, 1, '[5,4,1,3,7]', '$2a$10$yiVeVG8H2M52VwFP4Nmg2O3uVI2vJz72/tQg2KYthLyTfSW5zir7W');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (3, 1, '[7,0,5,1,4]', '$2a$10$HorVTNlMnZKg64PCV1KvCOb4R8o4nVNzfdpSD3qxyjW7VTnrckvpm');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (4, 1, '[8,6,5,7,2]', '$2a$10$v7dNZ0CE2.kAa6x5izMbFO.8yMbnrT2LlEnFA/EFTe3OR4jVi/jlS');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (5, 1, '[2,1,0,7,5]', '$2a$10$yRrn6mWdDgLw19X4zqRMZ.wubcidAaAh9ydrghSGZtwV0kWx2xQqa');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (6, 1, '[0,6,1,2,7]', '$2a$10$nFvEUl/vVc3SFFn3GClGc.YDYcfmvmPCpPFctxDpwkmd/V5jlzsOu');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (7, 1, '[8,0,2,4,3]', '$2a$10$U5G0iLAnkCywXGiRbqb83OHSOQV2AlgbFI4nlaAS10BPCJP7P28xO');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (8, 1, '[1,4,5,7,3]', '$2a$10$216.i.VtXSbivONvIvtQ8OYMb4f3IsANOIigGKhrBAluNtoUlMkky');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (9, 1, '[8,3,2,4,1]', '$2a$10$Yk6yps/IIExJ4WHcIygeIe595SlhNvboKaSB6w6A.Vds4W3Wc/g8S');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (10, 1, '[3,2,5,1,4]', '$2a$10$dch3I9GCTteqX6X0ihQqCeYlu1.320Aqgbf.TXuKJXBl6Hq9gAqee');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (11, 2, '[2,3,4,8,1]', '$2a$10$7vwfNljxY4j3x7ds6iQe0eEcbS9JPKUujSKwUuuwaMEoNz9MLstYq');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (12, 2, '[8,3,7,2,6]', '$2a$10$g2AKu0PvrWqQy5h80gagm.AtTNdF56NCP8FQAcK6PC10SWy/YlXTK');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (13, 2, '[4,6,5,0,8]', '$2a$10$JOQALspszLHsjp2stU8EouK1Lff6uvzLiPx0RF8nfPH0sbiNisCwO');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (14, 2, '[0,4,3,7,6]', '$2a$10$fUi2hpBKyF9rPLAzWSaV1.apwckmdIY1peY8tPA.6eLVVUGGY/oOy');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (15, 2, '[4,3,6,7,8]', '$2a$10$TjUTBzKI/rB5.XG/beLMvemklCx4UtIqBNH6Ewza82nQT0qoj/Zpi');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (16, 2, '[0,2,3,4,1]', '$2a$10$E5bsYH0XidB7yueDpCThCOR.ShNBkReqRWCjABUlKI.ekhQ879nWO');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (17, 2, '[5,8,0,7,2]', '$2a$10$SbpTf2NLXy9FJjpF9yZkpuEzc3g1Gi5.P9LgFfZK9XndbN3tJjg/q');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (18, 2, '[5,1,2,4,8]', '$2a$10$KxIskQOmjQy54MUJ2lw3M.Z3rNtavciyRJf4nfye3mwW6P7Ar3FCe');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (19, 2, '[1,8,4,5,2]', '$2a$10$bOevZZK.GVfDacdiQT.L6.YsyKmITZsmecpEBHMNuCpEcwSxAEpJy');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (20, 2, '[5,3,6,4,2]', '$2a$10$q4wZ9smzdcXH78Ofw1BjrOuZU/810H9roMcFOyv5sZFVRRWjDVuOK');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (21, 3, '[0,6,2,5,1]', '$2a$10$TVynmd.ohNbFGsJbLbYhXeNw1p7GA9t81TzfipOIeWrw5DFRcoxdK');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (22, 3, '[6,8,5,4,1]', '$2a$10$DpXYPVGossr68fw2ZxH7WuoGdL1Pn9u7loh5.xqvFaBh9Cqt0F8ya');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (23, 3, '[4,7,0,5,2]', '$2a$10$WH46QeFG2bka9JF5AL0p9Ow0kyQORHk2I9AMi/TAwTpzxioM2jPg2');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (24, 3, '[4,6,8,7,1]', '$2a$10$7Q93pbNRMIsrgDHUIreYpeP0nSPZ9py8aKAkVf1xHJn5eCbsW6ETm');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (25, 3, '[1,5,8,7,3]', '$2a$10$26w28jX7wwApp3PBIyTnReZZ5x6EqvoSx7q.C1SZ4OqKKpBTF0ulS');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (26, 3, '[2,7,0,8,1]', '$2a$10$2w9bJ4N4UlnOnmtHP5R0F.cGCtEI5t.J2v9hgiMA6WP9oS57YdLZG');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (27, 3, '[7,0,5,8,1]', '$2a$10$.lhMINeBHOOa0Ha/4djh/uwRuxrBwoV6RAfrQTRazuKU6EbRcHLda');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (28, 3, '[4,1,6,8,0]', '$2a$10$ru9Z3evnXkzXiNTs6sUj1elRlv3U.oGzr1fSnupGBUK/zuuyYs1eq');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (29, 3, '[8,6,0,5,2]', '$2a$10$Sy/BY97gYwkoe7aUhyrgxeyyb/3m6QXduVtux1dcZJ6LmTiNrZ8Fy');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (30, 3, '[5,1,7,4,8]', '$2a$10$CnRTe9NbZqvKKToZ5bpZLOg9t7F4LMMXTdnVmm2MNpWDTzhRsk1tK');

-- MESSAGE

INSERT INTO MESSAGE (ID, CONTENT, OWNER_ID) VALUES (1, 'Wsiada baba do windy a tam schody', 1);
INSERT INTO MESSAGE (ID, CONTENT, OWNER_ID) VALUES (2, 'Wchodzi popek do salonu Ery i prosi telefon na firme', 1);
INSERT INTO MESSAGE (ID, CONTENT, OWNER_ID) VALUES (3, 'Dlaczego murzyn nie może być żydem? Bo bez przesady.', 2);
INSERT INTO MESSAGE (ID, CONTENT, OWNER_ID) VALUES (4, '0.7 zgłoś się', 2);
INSERT INTO MESSAGE (ID, CONTENT, OWNER_ID) VALUES (5, 'pehap pehap pehap pfffffff', 2);

-- ALLOWED_MESSAGE

INSERT INTO ALLOWED_MESSAGE (USER_ID, MESSAGE_ID) VALUES (1, 3);
INSERT INTO ALLOWED_MESSAGE (USER_ID, MESSAGE_ID) VALUES (3, 1);
INSERT INTO ALLOWED_MESSAGE (USER_ID, MESSAGE_ID) VALUES (3, 2);
INSERT INTO ALLOWED_MESSAGE (USER_ID, MESSAGE_ID) VALUES (3, 5);
