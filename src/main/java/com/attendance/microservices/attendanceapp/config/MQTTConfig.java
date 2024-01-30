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




    // *********** Not Recommended Approach ************
    // @Value("${aws.iot.clientEndpoint}")
    // String clientEndpoint;

    // @Value("${aws.iot.clientId}")
    // String clientId;

    // @Value("${aws.iot.accessKeyId}")
    // String awsAccessKeyId;

    // @Value("${aws.iot.secretAccessKey}")
    // String awsSecretAccessKey;


    // // Connection to client
    // AWSIotMqttClient client = null;


    // public void connectToAWS() throws AWSIotException {
    //     // AWS IAM credentials could be retrieved from AWS Cognito, STS, or other secure sources
    //     client = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey, null);

    //     // optional parameters can be set before connect()
    //     client.connect();
    //     System.out.println("Connected to AWS IoT.");
    // }


    // public void publish(String payload) throws AWSIotException, JsonProcessingException {
    //     String topic = "attendanceTest";
    //     AWSIotQos qos = AWSIotQos.QOS0;
    //     long timeout = 3000; // milliseconds

    //     MyMessage message = new MyMessage(topic, qos, payload);
    //     client.publish(message, timeout);
    // }
}
