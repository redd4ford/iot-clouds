input: iot hub, output: SQL database
```
SELECT
    value as value,
    measureTime as measure_time,
    deviceId as device_id,
    protocol as protocol
INTO
    [database]
FROM
    [device]
```
