package com.attendance.microservices.attendanceapp.config;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.services.iotdata.model.PublishRequest;

import io.jsonwebtoken.io.IOException;

@Configuration
public class MQTTConfig {

    @Autowired
    private AwsConfig awsConfig;

    @Autowired
    private AwsAppConfig awsAppConfig;

    public void publishToShadow() throws IOException {
        String topic = "$aws/things/AttendanceTaker/shadow/name/barcode_attendance/update";
        String payload = "{\"state\":{\"reported\":{\"sensor\":3.0}}}";
        ByteBuffer bb = StandardCharsets.UTF_8.encode(payload);

        PublishRequest publishRequest = new PublishRequest();
        publishRequest.withPayload(bb);
        publishRequest.withTopic(topic);
        publishRequest.setQos(0);


        awsConfig.getIotDataClient(awsAppConfig).publish(publishRequest);
        System.out.println("Message Published Successfully.");

    }
}
