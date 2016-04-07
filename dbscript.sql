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
VALUES (1, 'antek', NULL, NULL, NULL, '$2a$10$TBygVhH4SadL8QV.z1UGKOSyxira3rfcSn4nAGvB0v3b4nyi5LByq', 1, 0, 3, 3, 1,
        9); -- Antek123!
INSERT INTO `user_profile` (`id`, `login`, `last_login_date`, `LAST_FAILED_LOGIN_DATE`, `BLOCKADE_DEADLINE`, `PASSWORD`, `IS_ACTIVE`, `NO_OF_FAILED_LOGINS_SINCE_LAST`, `NO_OF_ATTEMPTS_BEFORE_BLOCKADE`, `DELAY_IN_SECONDS`, `CURRENT_PASSWORD_FRAGMENT_ID`, `PASSWORD_LENGTH`)
VALUES (2, 'bolek', NULL, NULL, NULL, '$2a$10$zeYubYCCCjjCwkEnH.Djd.O1HabXrZ4SgW5EIdNdSsgHt55pVyFnu', 1, 0, 3, 3, 11,
        9); -- Bolek123!
INSERT INTO `user_profile` (`id`, `login`, `last_login_date`, `LAST_FAILED_LOGIN_DATE`, `BLOCKADE_DEADLINE`, `PASSWORD`, `IS_ACTIVE`, `NO_OF_FAILED_LOGINS_SINCE_LAST`, `NO_OF_ATTEMPTS_BEFORE_BLOCKADE`, `DELAY_IN_SECONDS`, `CURRENT_PASSWORD_FRAGMENT_ID`, `PASSWORD_LENGTH`)
VALUES (3, 'lolek', NULL, NULL, NULL, '$2a$10$kxmsLKFF0RlC6tUv1/JthuRVp6j79tl.nLvkas14KYdxy9M36VYpy', 1, 0, 3, 3, 21,
        9); -- Lolek123!

-- PASSWORD_FRAGMENT


INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (1, 1, '[2,3,4,6,8]', '$2a$10$Aze/mY3WtBIWdbuPoRwZoeOl2gFVkXMohwSQo15d5ci0Tm39ZyO8i');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (2, 1, '[0,1,3,5,8]', '$2a$10$vJSCTH5y.ZVR5/Zq2RrhxOq0829mTECKbIyOAjS9F1wISHlzoR5yK');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (3, 1, '[0,1,4,7,8]', '$2a$10$DWsaMKiiFE79IKM3ExE9qOIazP7a98jFgUr64mkglDoeHQ1JMYFW2');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (4, 1, '[1,2,3,7,8]', '$2a$10$Tdkylvhw1Sjodi35jrBlgudXtnu139KGOWGLxKmPFH3p5q0moaoPG');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (5, 1, '[0,1,3,5,7]', '$2a$10$NVOynX0aOA4NWFgBj48FbeuGJAh60MMgODbLA6DIUtQBZp3KZ6Uci');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (6, 1, '[0,1,2,3,7]', '$2a$10$kZ8I/AJuNHcAJy21XfuVheG2QBAm63mEBzp5aGZq2zuD2eO09A0lO');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (7, 1, '[0,2,3,4,7]', '$2a$10$n.K/wdXi1udrrf12db4sreZtaaozZ5Y/a1B4WEh0gl11vLRoQzsQC');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (8, 1, '[1,2,3,5,8]', '$2a$10$DtDItRp9d4OmG.5iyrZWC.X7gNJv.QV1cYQmG17NVyIJGJfpdYnj6');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (9, 1, '[1,2,6,7,8]', '$2a$10$ifMAcgSFvK/D9i5LLq/QVOR7XDZDWqoEp4EBvUrXcytaxChO1QlvK');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (10, 1, '[2,4,5,7,8]', '$2a$10$9r4yeWECu2cWI4geWkwX0e96NzB0iFEagurL5RH5P7MLfaAzu5YNe');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (11, 2, '[0,1,5,6,8]', '$2a$10$ozjcbxCwu/kz7J3IQ8wROulnhL2A7rwvCJyz6q161Tmnk49PAAcYm');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (12, 2, '[0,4,5,6,8]', '$2a$10$.VMQQF72gfMTWzZDFbadfuZc7xCHC9Axh9LZ7Fyi23TerLk.bpRQ6');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (13, 2, '[0,2,3,7,8]', '$2a$10$sIxxSn0IVAn6pVPRhaTGbO/9q52bLsLRkVRv.51iz3k/Ilq0Tm4S2');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (14, 2, '[1,3,4,5,8]', '$2a$10$eMBO..f1x6dSf8aRwCuU3.cV6WPO/nEcOf5JHFkHsSTNwSLqCsKya');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (15, 2, '[0,1,2,3,4]', '$2a$10$W5tFAqHCpOkcHK9MYzVW..uUVTlts0Hv46CulmsLwzwkPWGFTNcTe');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (16, 2, '[0,3,4,5,6]', '$2a$10$H5QdiFz8mdddunq5sIS.d.5xiNZKJRDWZfwch3CyhH9QR4ZIEOdgq');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (17, 2, '[0,1,4,6,8]', '$2a$10$xjgAOvLzzYChESIeFw8flO3T7Z7eH82rWDhbxz7g/OdxyB4uK.jpa');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (18, 2, '[0,2,3,4,7]', '$2a$10$7Uvo0f9yiW2yP3J3s/hEieeXBWfg2t1XQdbZS1uMhsUstkwlkwpne');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (19, 2, '[0,3,4,6,8]', '$2a$10$9XsAuV4A7Ka.oUtDPfDchOB6ycK7yg1xpxpdwYmLLZryTxy7Xz0jy');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (20, 2, '[1,3,4,5,6]', '$2a$10$GEr6og/oyKW..xznIIBt0uvnU6YngoBn5NDPhJRqMV2IV3S/tVp6S');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (21, 3, '[0,2,3,5,6]', '$2a$10$h8ZBOHVsKyyDEf3o3P2Nx.pob5eapLafIjI1LjGmMz3urTTCrkX6W');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (22, 3, '[0,1,3,7,8]', '$2a$10$1hxHceTM4cTOJChRHEl1Pu0sooaVZUiEH8sa.bHv/EIW2R7wQwy8K');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (23, 3, '[2,4,5,6,7]', '$2a$10$3lvKNsBg4G6P1qxpMoxtKevx0jkgzyQNgHTtQZe62A4e667NTFE0e');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (24, 3, '[0,1,2,3,7]', '$2a$10$C2Enmb6Yl/zcJkw3WfhsU.7mZZpddn8xuTNCRKlyCHRomKa3C8CSm');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (25, 3, '[0,1,2,4,8]', '$2a$10$ZxW2L4nhliU/Gy.Sk31ad.iNdeuYslaUQt72NyQSj/SG9rE5BHH9K');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (26, 3, '[1,2,5,7,8]', '$2a$10$tAJjLBsU4KhC1b3cABy7m.vgtkRmXb6NCgIg/3pzI2qrP1741LduS');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (27, 3, '[0,3,4,5,8]', '$2a$10$lUyZiNBh/u9S3ACwGOwwbeRD66nRfMObBVqO1952FJcjropC3eZV6');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (28, 3, '[1,2,3,4,5]', '$2a$10$B871/H5yiiCwPAboFNPiIeV2/E8sy71esffYiLST9BcLmlvAn1f/C');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (29, 3, '[0,2,3,4,8]', '$2a$10$w2X9bnzuzYrpuR3G5Yxb.OIiALExA3zWwWpCcf0VWwC2QjebeuhKi');
INSERT INTO `password_fragment` (`id`, `USER_ID`, `JSON_MASK`, `PASSWORD_HASH`)
VALUES (30, 3, '[0,1,5,6,8]', '$2a$10$8/DgbobXXj/KF2Tt7sZcTuyJszKgHXb.gANP59c0KadpoYzYuWq1.');

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
