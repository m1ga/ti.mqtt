const mqtt = require("ti.mqtt");
const win = Ti.UI.createWindow({
	layout: "vertical"
});
const btn1 = Ti.UI.createButton({
	title: "connect"
});
const btn2 = Ti.UI.createButton({
	title: "subscribe"
});
const btn3 = Ti.UI.createButton({
	title: "publish"
});

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

btn1.addEventListener("click", e => {
	mqtt.createClient({
		server: "192.168.0.2",
		port: 1883
	})
	mqtt.connect();
})

btn2.addEventListener("click", e => {
	mqtt.subscribe({
		topic: "topic"
	})
})

btn3.addEventListener("click", e => {
	mqtt.publish({
		topic: "topic",
		payload: "test message"
	})
})

win.add([btn1, btn2, btn3]);
win.open();
