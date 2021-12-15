CREATE DATABASE IF NOT EXISTS flightradar;

DROP TABLE IF EXISTS flightradar.plane;

CREATE TABLE flightradar.plane (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  speed_in_mph int NOT NULL DEFAULT '0',
  company varchar(45) NOT NULL,
  model varchar(45) NOT NULL,
  reg_number varchar(45) NOT NULL UNIQUE,
  departure_airport varchar(45),
  arrival_airport varchar(45),
  scheduled_departure timestamp NOT NULL,
  scheduled_arrival timestamp NOT NULL
);

CREATE UNIQUE INDEX plane_reg_number_idx ON flightradar.plane (reg_number);

START TRANSACTION;
INSERT INTO flightradar.plane (id, speed_in_mph, company, model, reg_number, departure_airport, arrival_airport, scheduled_departure, scheduled_arrival) VALUES
(1, 200, 'LOT', 'Boeing 737 MAX 8', 'SP-LVB', 'WAW', 'TLV', '2002-01-01 00:00:20', '2002-01-01 00:05:00'),
(2, 1, 'LOT', 'Embraer E175LR', 'SP-LIN', 'WAW', 'LUZ', '2003-01-01 00:00:20', '2003-01-01 00:05:00'),
(3, 228, 'Azur Air Ukraine', 'Boeing 757-3E7', 'UR-AZP', 'SSH', 'HRK', '2004-01-01 00:00:20', '2004-01-01 00:05:00'),
(4, 42, 'Finnair', 'ATR 72-500', 'OH-ATE', 'HEL', 'OSL', '2005-01-01 00:00:20', '2005-01-01 00:05:00');
COMMIT;
