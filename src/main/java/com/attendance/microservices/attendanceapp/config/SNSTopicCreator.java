// package com.attendance.microservices.attendanceapp.config;

// import software.amazon.awssdk.services.codestarnotifications.model.SubscribeRequest;
// import software.amazon.awssdk.services.sns.SnsClient;
// import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
// import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
// import software.amazon.awssdk.services.sns.model.SnsException;

// import software.amazon.awssdk.services.sqs.SqsClient;
// import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
// import software.amazon.awssdk.services.sqs.model.CreateQueueResponse;
// import software.amazon.awssdk.services.sqs.model.SqsException;

// public class SNSTopicCreator {

//     public static String createSNSTopic(SnsClient snsClient, String topicName) {
//         CreateTopicResponse result = null;
//         try {
//             CreateTopicRequest request = CreateTopicRequest.builder()
//                     .name(topicName)
//                     .build();

//             result = snsClient.createTopic(request);
//             return result.topicArn();
//         } catch (SnsException e) {
//             System.err.println(e.awsErrorDetails().errorMessage());
//             System.exit(1);
//         }
//         return "";
//     }

//     // Assume you have an existing SQS client (sqsClient) and specify your queue
//     // name
//     public static String createSQSQueue(SqsClient sqsClient, String queueName) {
//         CreateQueueResponse result = null;
//         try {
//             CreateQueueRequest request = CreateQueueRequest.builder()
//                     .queueName(queueName)
//                     .build();

//             result = sqsClient.createQueue(request);
//             return result.queueUrl();
//         } catch (SqsException e) {
//             System.err.println(e.awsErrorDetails().errorMessage());
//             System.exit(1);
//         }
//         return "";
//     }


//     // Subscribe to topic
//     public static void subscribeSQSToTopic(SnsClient snsClient, String topicArn, String queueUrl) {
//         try {
//             SubscribeRequest subscribeRequest = SubscribeRequest.builder()
//                     .arn(topicArn)
//                     .clientRequestToken(queueUrl)
//                     .build();

//             snsClient.subscribe(subscribeRequest);
//             System.out.println("Subscribed SQS queue to topic: " + topicArn);
//         } catch (SnsException e) {
//             System.err.println(e.awsErrorDetails().errorMessage());
//             System.exit(1);
//         }
//     }

// }
