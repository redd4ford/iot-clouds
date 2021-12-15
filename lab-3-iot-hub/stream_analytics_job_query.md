input: iot hub, output: post-function
```
SELECT
    deviceId as device_id,
    value as value,
    measureTime as measure_time,
    protocol as protocol
INTO
    [post-function]
FROM
    [sensor]
```
