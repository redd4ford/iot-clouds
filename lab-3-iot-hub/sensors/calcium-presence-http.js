// stolen from Microsoft

'use strict';

// const Protocol = require('azure-iot-device-mqtt').Mqtt;
// const Protocol = require('azure-iot-device-amqp').Amqp;
const Protocol = require('azure-iot-device-http').Http;
// const Protocol = require('azure-iot-device-mqtt').MqttWs;
// const Protocol = require('azure-iot-device-amqp').AmqpWs;

const Client = require('azure-iot-device').Client;
const Message = require('azure-iot-device').Message;

const deviceConnectionString = 'HostName=water-quality-iot-hub.azure-devices.net;DeviceId=calcium-presence-http;SharedAccessKey=/jwUi7W2RA96TVuOSLSmtFN3V3A1VX+FKPUrbZhfS8w=';
let sendInterval;

function disconnectHandler () {
  clearInterval(sendInterval);
  sendInterval = null;
  client.open().catch((err) => {
    console.error(err.message);
  });
}

// The AMQP and HTTP transports have the notion of completing, rejecting or abandoning the message.
// For example, this is only functional in AMQP and HTTP:
// client.complete(msg, printResultFor('completed'));
// If using MQTT calls to complete, reject, or abandon are no-ops.
// When completing a message, the service that sent the C2D message is notified that the message has been processed.
// When rejecting a message, the service that sent the C2D message is notified that the message won't be processed by the device. the method to use is client.reject(msg, callback).
// When abandoning the message, IoT Hub will immediately try to resend it. The method to use is client.abandon(msg, callback).
// MQTT is simpler: it accepts the message by default, and doesn't support rejecting or abandoning a message.
function messageHandler (msg) {
  console.log(`Id: ${msg.messageId} | Body: ${msg.data}`);
  client.complete(msg, printResultFor('completed'));
}

function getTimestamp (timestamp) {
    const pad = (n,s=2) => (`${new Array(s).fill(0)}${n}`).slice(-s);  
    return `${pad(timestamp.getFullYear(),4)}-${pad(timestamp.getMonth()+1)}-${pad(timestamp.getDate())}T${pad(timestamp.getHours())}:${pad(timestamp.getMinutes())}:${pad(timestamp.getSeconds())}`;
  }  

function generateMessage () {
  const calciumPresence = Math.random() * 100; // range: [0, 100]
  var timestamp = new Date();
  const data = JSON.stringify(
    {
      deviceId:    'calciumPresenceDevice',
      value:       calciumPresence,
      measureTime: getTimestamp(timestamp),
      protocol:    'http'
    }
  );
  const message = new Message(data);
  message.properties.add('calciumPresenceAlert', (calciumPresence > 15) ? 'true' : 'false');
  return message;
}

function errorHandler (err) {
  console.error(err.message);
}

function connectHandler () {
  console.log('Client connected');
  if (!sendInterval) {
    sendInterval = setInterval(() => {
      const message = generateMessage();
      console.log('Sending message: ' + message.getData());
      client.sendEvent(message, printResultFor('send'));
    }, 10000);
  }
}

let client = Client.fromConnectionString(deviceConnectionString, Protocol);

client.on('connect', connectHandler);
client.on('error', errorHandler);
client.on('disconnect', disconnectHandler);
client.on('message', messageHandler);

client.open()
.catch(err => {
  console.error('Could not connect: ' + err.message);
});

function printResultFor(op) {
    return function printResult(err, res) {
      if (err) console.log(`${op} error: ${err.toString()}`);
      if (res) console.log(`${op} status: ${res.constructor.name}`);
    };
  }