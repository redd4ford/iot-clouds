CREATE DATABASE IF NOT EXISTS steam;

DROP TABLE IF EXISTS steam.account_has_supported_browser;
DROP TABLE IF EXISTS steam.supported_browser;
DROP TABLE IF EXISTS steam.account_has_game;
DROP TABLE IF EXISTS steam.game_has_genre;
DROP TABLE IF EXISTS steam.genre;
DROP TABLE IF EXISTS steam.game_has_platform;
DROP TABLE IF EXISTS steam.platform;
DROP TABLE IF EXISTS steam.account_protected_data;
DROP TABLE IF EXISTS steam.account;
DROP TABLE IF EXISTS steam.game;
DROP TABLE IF EXISTS steam.publisher;
DROP TABLE IF EXISTS steam.country;

-- ------------------------------------------------- --
--                     COUNTRY                       --
-- ------------------------------------------------- --

CREATE TABLE steam.country (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(45) NOT NULL UNIQUE  
);

CREATE UNIQUE INDEX country_name_idx ON steam.country (name);

-- ------------------------------------------------- --
--                     ACCOUNT                       --
-- ------------------------------------------------- --


CREATE TABLE steam.account (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  country_id int NOT NULL,

  account_name varchar(45) NOT NULL UNIQUE,
  level int NOT NULL DEFAULT '0',
  is_online tinyint NOT NULL,

  CONSTRAINT `FK_account_country`
  FOREIGN KEY (country_id) REFERENCES country (id)
);

CREATE UNIQUE INDEX account_name_idx ON steam.account (account_name);


-- ------------------------------------------------- --
--                     PUBLISHER                     --
-- ------------------------------------------------- --


CREATE TABLE steam.publisher (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  country_id int NOT NULL,

  name varchar(45) NOT NULL UNIQUE,
  
  CONSTRAINT `FK_publisher_country`
  FOREIGN KEY (country_id) REFERENCES country (id)
);

CREATE UNIQUE INDEX publisher_name_idx ON steam.publisher (name);


-- ------------------------------------------------- --
--                       GAME                        --
-- ------------------------------------------------- --


CREATE TABLE steam.game (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  publisher_id int NOT NULL,

  title varchar(60) NOT NULL,
  rating tinyint NULL DEFAULT NULL,
  release_date varchar(60) NULL DEFAULT NULL,
  price_in_uah int NOT NULL DEFAULT '0',

  CONSTRAINT `FK_game_publisher`
  FOREIGN KEY (publisher_id) REFERENCES steam.publisher (id)
);

CREATE INDEX FK_game_publisher_idx ON steam.game (publisher_id);


-- ------------------------------------------------- --
--                  ACCOUNT_HAS_GAME                 --
-- ------------------------------------------------- --


CREATE TABLE steam.account_has_game (
  account_id int NOT NULL,
  game_id int NOT NULL,
  PRIMARY KEY(account_id, game_id),

  CONSTRAINT `FK_account_has_game_game`
  FOREIGN KEY (game_id) REFERENCES steam.game (id),

  CONSTRAINT `FK_account_has_game_account`
  FOREIGN KEY (account_id) REFERENCES steam.account (id)
);

CREATE INDEX FK_account_has_game_game_idx ON steam.account_has_game (game_id);
CREATE INDEX FK_account_has_game_account_idx ON steam.account_has_game (account_id);


-- ------------------------------------------------- --
--                 SUPPORTED_BROWSER                 --
-- ------------------------------------------------- --


CREATE TABLE steam.supported_browser (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,

  name varchar(45) NOT NULL,
  version varchar(45) NOT NULL
);


-- ------------------------------------------------- --
--           ACCOUNT_HAS_SUPPORTED_BROWSER           --
-- ------------------------------------------------- --


CREATE TABLE steam.account_has_supported_browser (
  account_id int NOT NULL,
  supported_browser_id int NOT NULL,
  PRIMARY KEY(account_id, supported_browser_id),

  CONSTRAINT `FK_account_has_supported_browser_supported_browser`
  FOREIGN KEY (supported_browser_id) REFERENCES steam.supported_browser (id),

  CONSTRAINT `FK_account_has_supported_browser_account`
  FOREIGN KEY (account_id) REFERENCES steam.account (id)
);

CREATE INDEX FK_account_has_supported_browser_account_idx ON steam.account_has_supported_browser (account_id);
CREATE INDEX FK_account_has_supported_browser_browser_idx ON steam.account_has_supported_browser (supported_browser_id);


-- ------------------------------------------------- --
--               ACCOUNT_PROTECTED_DATA              --
-- ------------------------------------------------- --


CREATE TABLE steam.account_protected_data (
  account_id int NOT NULL PRIMARY KEY,

  password varchar(45) NOT NULL,
  email varchar(45) NOT NULL UNIQUE,

  CONSTRAINT `FK_account_protected_data`
  FOREIGN KEY (account_id) REFERENCES account (id)
);

CREATE INDEX FK_account_protected_data_account_idx ON steam.account_protected_data (account_id);
CREATE UNIQUE INDEX account_protected_data_email_idx ON steam.account_protected_data (email);


-- ------------------------------------------------- --
--                       GENRE                       --
-- ------------------------------------------------- --


CREATE TABLE steam.genre (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(30) UNIQUE NOT NULL
);


-- ------------------------------------------------- --
--                  GAME_HAS_GENRE                   --
-- ------------------------------------------------- --


CREATE TABLE steam.game_has_genre (
  game_id int NOT NULL,
  genre_id int NOT NULL,
  PRIMARY KEY(game_id, genre_id),

  CONSTRAINT `FK_game_has_genre_game`
  FOREIGN KEY (game_id) REFERENCES steam.game (id),

  CONSTRAINT `FK_game_has_genre_genre`
  FOREIGN KEY (genre_id) REFERENCES steam.genre (id)
);

CREATE INDEX FK_game_has_genre_game_idx ON steam.game_has_genre (game_id);
CREATE INDEX FK_game_has_genre_genre_idx ON steam.game_has_genre (genre_id);


-- ------------------------------------------------- --
--                      PLATFORM                     --
-- ------------------------------------------------- --


CREATE TABLE steam.platform (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(45) NOT NULL
);


-- ------------------------------------------------- --
--                 GAME_HAS_PLATFORM                 --
-- ------------------------------------------------- --


CREATE TABLE steam.game_has_platform (
  game_id int NOT NULL,
  platform_id int NOT NULL,
  PRIMARY KEY(game_id, platform_id),

  CONSTRAINT `FK_game_has_platform_game`
  FOREIGN KEY (game_id) REFERENCES steam.game (id),

  CONSTRAINT `FK_game_has_platform_platform`
  FOREIGN KEY (platform_id) REFERENCES steam.platform (id)
);

CREATE INDEX FK_game_has_platform_game_idx ON steam.game_has_platform (game_id);
CREATE INDEX FK_game_has_platform_platform_idx ON steam.game_has_platform (platform_id);


-- ------------------------------------------------- --
--                      INSERT                       --
-- ------------------------------------------------- --


START TRANSACTION;
INSERT INTO steam.country (id, name) VALUES
(1, 'Ukraine'), (2, 'Russia'), (3, 'USA'), (4, 'UK'), (5, 'France'),
(6, 'Germany'), (7, 'Norway'), (8, 'Poland'), (9, 'Japan'), (10, 'Brazil');
COMMIT;

START TRANSACTION;
INSERT INTO steam.publisher (id, name, country_id) VALUES
(1, 'Electronic Arts', 3),
(2, 'Heart Machine', 3),
(3, 'Re-Logic', 3),
(4, 'Bethesda Softworks', 3),
(5, 'tobyfox', 3),
(6, 'Private Division', 3),
(7, 'tinyBuild', 2),
(8, 'Ubisoft', 5),
(9, 'Nintendo', 9),
(10, 'Activision Blizzard', 3);
COMMIT;

START TRANSACTION;
INSERT INTO steam.game (id, title, rating, release_date, price_in_uah, publisher_id) VALUES
(1, 'Fallout 3', 91, '2008-10-28', 260, 4),
(2, 'Fallout 4', 84, '2015-11-10', 780, 4),
(3, 'Terraria', 83, '2011-05-16', 169, 3),
(4, 'Undertale', 92, '2015-09-15', 169, 5),
(5, 'Hyper Light Drifter', 84, '2016-03-31', 279, 2),
(6, 'The Outer Worlds', 82, '2019-10-25', 669, 6),
(7, 'Pathologic 2', 91, '2019-05-23', 429, 7),
(8, 'Watch_Dogs', 77, '2014-05-26', 459, 8),
(9, 'Spore', 84, '2008-12-19', 174, 1),
(10, 'The Sims 4', 86, '2014-09-02', 1199, 1);
COMMIT;

START TRANSACTION;
INSERT INTO steam.genre (id, name) VALUES
(1, 'Adventure'), (2, 'Casual'),
(3, 'Horror'), (4, 'Open World'),
(5, 'Pixel Art'), (6, 'RPG'),
(7, 'Sandbox'), (8, 'Shooter'),
(9, 'Survival'), (10, 'Action');
COMMIT;

START TRANSACTION;
INSERT INTO steam.game_has_genre (game_id, genre_id) VALUES
(1, 1), (2, 1), (3, 1), (4, 1), (5, 1), (6, 1),
(7, 1), (8, 1), (3, 2), (9, 2), (10, 2), (1, 3),
(7, 3), (1, 4), (2, 4), (9, 4), (3, 5), (4, 5),
(5, 5), (1, 6), (2, 6), (4, 6), (6, 6), (7, 6),
(8, 6), (10, 6), (2, 7), (3, 7), (9, 7), (1, 8),
(6, 8), (8, 8), (7, 9), (10, 9), (5, 10), (6, 10),
(8, 10);
COMMIT;

START TRANSACTION;
INSERT INTO steam.platform (id, name) VALUES
(1, 'Windows OS'), (2, 'Linux OS'),
(3, 'Mac OS X'), (4, 'Android'),
(5, 'Windows Phone');
COMMIT;

START TRANSACTION;
INSERT INTO steam.game_has_platform (game_id, platform_id) VALUES
(1, 1), (2, 1), (3, 1), (4, 1), (5, 1), (6, 1),
(7, 1), (8, 1), (9, 1), (10, 1), (3, 2), (4, 2),
(5, 2), (3, 3), (4, 3), (5, 3);
COMMIT;

START TRANSACTION;
INSERT INTO steam.account (id, account_name, country_id, level, is_online) VALUES
(1, 'redd4ford', 1, 15, 1),
(2, 'Lant3rn', 3, 19, 0),
(3, 'aplehanov', 2, 22, 1),
(4, 'heckalaech', 2, 83, 0),
(5, 'X4-N', 4, 184, 0),
(6, 'RayVanhem', 4, 2, 1),
(7, 'FOLKEN_2MD', 6, 20, 1),
(8, 'Surgui', 7, 16, 0),
(9, 'Capella_', 5, 73, 1),
(10, 'Jerhyn', 5, 56, 0);
COMMIT;

START TRANSACTION;
INSERT INTO steam.account_protected_data (account_id, password, email) VALUES
(1, 'e4uT&9AX?X8W!=54', 'ohituziz@lykaon.com'),
(2, 'E6pm$Wu5.', 'nascetur.ridiculus.mus@atfringilla.co.uk'),
(3, 'Lpx`k6X9e', 'elit@fringilla.co.uk'),
(4, 'w>Um`n3VNb"', 'est@elit.net'),
(5, '1q12w123e1234r12345t123456y', 'ThomasAAllen@jourrapide.com'),
(6, 'S6p!}9x', 'scelerisque.lorem.ipsum@sed.com'),
(7, '2804496q', 'consequat@Phasellus.org'),
(8, 'password', 'ipsum@Naminterdum.com'),
(9, 'iloveyoujacob', 'Donec.felis@veliteusem.co.uk'),
(10, 'wX#{vV~BEqT2Q&Btr', 'magna.Cras.convallis@eu.co.uk');
COMMIT;

START TRANSACTION;
INSERT INTO steam.account_has_game (account_id, game_id) VALUES
(5, 1), (7, 1), (9, 1), (1, 2), (5, 2), (7, 2),
(8, 2), (9, 2), (2, 3), (7, 3), (10, 3), (3, 4),
(7, 4), (10, 4), (2, 5), (8, 5), (9, 5), (5, 6),
(8, 6), (1, 7), (5, 8), (6, 8), (2, 9), (5, 9),
(8, 9), (2, 10);
COMMIT;

START TRANSACTION;
INSERT INTO steam.supported_browser (id, name, version) VALUES
(1, 'Safari 7', '100500'), (2, 'Uranium', '100501'),
(3, 'Chrome', '1.100.1.0.1'), (4, 'Opera 3.5', '1.15.1'),
(5, 'Opera 3', '127.0.0.1'), (6, 'Firefox 1', '1.23.51'),
(7, 'Firefox', '435.12.1'), (8, 'Opera GX', '1'),
(9, 'Safari', '126215'), (10, 'Chrome', '1.11.1.1'),
(11, 'Opera', '1.15.3'), (12, 'Opera GX', '2');
COMMIT;

START TRANSACTION;
INSERT INTO steam.account_has_supported_browser (account_id, supported_browser_id) VALUES
(1, 2), (2, 3), (2, 4), (6, 5), (1, 6), (4, 6),
(10, 6), (5, 8), (6, 8), (9, 10), (3, 7), (3, 8),
(6, 4);
COMMIT;