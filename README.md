# ti.mqtt - MQTT Client for Titanium

Android MQTT client for Titanium using https://github.com/hivemq/hivemq-mqtt-client


```js
const mqtt = require("ti.mqtt");
mqtt.addEventListener("error", e => {
	console.log(e);
})
mqtt.addEventListener("connected", e => {
	console.log("connected");
})
mqtt.addEventListener("subscribed", e => {
	console.log("subscribed");
})
mqtt.addEventListener("message", e => {
	console.log("message");
	console.log("Payload:" + e.payload);
	console.log("Topic:" + e.topic);
})
const win = Ti.UI.createWindow({
	layout: "vertical"
});
const btn1 = Ti.UI.createButton({
	title: "connect"
});
btn1.addEventListener("click", e => {
	mqtt.createClient({
		server: "192.168.0.2",
		port: 1883
	})
	mqtt.connect();
})

const btn2 = Ti.UI.createButton({
	title: "subscribe"
});
btn2.addEventListener("click", e => {
	mqtt.subscribe({
		topic: "topic"
	})
})
const btn3 = Ti.UI.createButton({
	title: "publish"
});
btn3.addEventListener("click", e => {
	mqtt.publish({
		topic: "topic",
		payload: "test message"
	})
})

win.add([btn1, btn2, btn3]);
win.open();
```
