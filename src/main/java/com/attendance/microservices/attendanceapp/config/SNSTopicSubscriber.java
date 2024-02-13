package com.attendance.microservices.attendanceapp.config;

import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;

public class SNSTopicSubscriber {
    
    public void subscribeToEndpoint(String topicArn, String requestEndpoint, SnsClient snsClient) {

        // Subscribe the SQS queue to the SNS topic
        SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .topicArn(topicArn)
                .protocol("sqs")
                .endpoint(requestEndpoint)
                .build();

        SubscribeResponse subscribeResponse = snsClient.subscribe(subscribeRequest);
        System.out.println("Subscribed SQS queue to topic: " + subscribeResponse.subscriptionArn());

        // // Create an SQS queue
        // CreateQueueResponse createQueueResponse =
        // sqsClient.createQueue(CreateQueueRequest.builder()
        // .queueName(queueName)
        // .attributes(attributes) // Optional: Set retention period (in seconds)
        // .build());

        // String queueUrl = createQueueResponse.queueUrl();

        // // Subscribe the SQS queue to the SNS topic
        // Topic.subscribeQueue(snsClient, sqsClient, topicArn, queueUrl);

        // System.out.println("Subscribed SQS queue to topic: " + topicArn);
    }
}
