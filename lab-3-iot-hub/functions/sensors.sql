CREATE DATABASE IF NOT EXISTS sensors;

DROP TABLE IF EXISTS sensors.sensor_data;


CREATE TABLE sensors.sensor_data (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  value float NOT NULL DEFAULT '0',
  measure_time timestamp NOT NULL,
  device_id varchar(45) NOT NULL,
  protocol varchar(4) NOT NULL
);
