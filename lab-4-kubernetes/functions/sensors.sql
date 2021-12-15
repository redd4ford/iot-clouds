CREATE TABLE sensor_data (
  id int NOT NULL IDENTITY(1, 1),
  value decimal(5,2) NOT NULL,
  measure_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  device_id char(45) NOT NULL,
  protocol char(4) NOT NULL,
  CONSTRAINT id_pk PRIMARY KEY(id)
);
