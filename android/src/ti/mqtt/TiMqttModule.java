/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2018 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
package ti.mqtt;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.TiConfig;
import org.appcelerator.titanium.TiApplication;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RequiresApi(api = Build.VERSION_CODES.N)
@Kroll.module(name = "TiMqtt", id = "ti.mqtt")
public class TiMqttModule extends KrollModule {

    // Standard Debugging variables
    private static final String LCAT = "TiMqttModule";
    private static final boolean DBG = TiConfig.LOGD;
    Mqtt3AsyncClient client;

    // You can define constants with @Kroll.constant, for example:
    // @Kroll.constant public static final String EXTERNAL_NAME = value;

    public TiMqttModule() {
        super();
    }

    @Kroll.onAppCreate
    public static void onAppCreate(TiApplication app) {
    }

    // Methods
    @Kroll.method
    public void createClient(KrollDict kd) {
        String server = kd.getString("server");
        int port = kd.getInt("port");
        client = MqttClient.builder()
                .identifier(UUID.randomUUID().toString())
                .serverHost(server)
                .serverPort(port)
                .useMqttVersion3()
                .buildAsync();
    }

    @Kroll.method
    public void connect() {
        client.connect().whenComplete((mqtt5ConnAck, throwable) -> {
            if (throwable != null) {
                fireEvent("error", new KrollDict());
            } else {
                fireEvent("connected", new KrollDict());
            }
        });
    }

    @Kroll.method
    public void publish(KrollDict kd) {
        String topic = kd.getString("topic");
        String payload = kd.getString("payload");
        client.publishWith()
                .topic(topic)
                .payload(payload.getBytes(StandardCharsets.UTF_8))
                .qos(MqttQos.EXACTLY_ONCE)
                .send()
                .whenComplete((mqtt5PublishResult, throwable) -> {
                    if (throwable != null) {
                        fireEvent("error", new KrollDict());
                    } else {
                        fireEvent("published", new KrollDict());
                    }
                });
    }

    @Kroll.method
    public void subscribe(KrollDict kd) {
        String topic = kd.getString("topic");
        client.subscribeWith()
                .topicFilter(topic)
                .callback(publish -> {
                    String message = new String(publish.getPayloadAsBytes());
                    KrollDict kdpayload = new KrollDict();
                    kdpayload.put("payload", message);
                    kdpayload.put("topic", publish.getTopic().toString());
                    fireEvent("message", kdpayload);
                })
                .send()
                .whenComplete((mqtt5PublishResult, throwable) -> {
                    if (throwable != null) {
                        fireEvent("error", new KrollDict());
                    } else {
                        fireEvent("subscribed", new KrollDict());
                    }
                });
    }

    @Kroll.method
    public void unsubscribe(KrollDict kd) {
        String topic = kd.getString("topic");
        client.unsubscribeWith()
                .topicFilter(topic)
                .send();
    }

    @Kroll.method
    public void disconnect() {
        client.disconnect();
    }


	/*

	// Properties
	@Kroll.getProperty
	public String getExampleProp()
	{
		Log.d(LCAT, "get example property");
		return "hello world";
	}


	@Kroll.setProperty
	public void setExampleProp(String value) {
		Log.d(LCAT, "set example property: " + value);
	}
*/
}

