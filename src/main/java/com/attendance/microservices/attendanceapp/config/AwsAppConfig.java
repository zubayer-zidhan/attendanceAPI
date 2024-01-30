package com.attendance.microservices.attendanceapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class AwsAppConfig {
    @Value("${aws.clientEndpoint}")
    String clientEndpoint;

    @Value("${aws.accessKeyId}")
    String awsAccessKeyId;

    @Value("${aws.secretAccessKey}")
    String awsSecretAccessKey;
}
