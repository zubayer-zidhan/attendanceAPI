package com.attendance.microservices.attendanceapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.iot.AWSIot;
import com.amazonaws.services.iot.AWSIotClientBuilder;
import com.amazonaws.services.iotdata.AWSIotDataClient;
import com.amazonaws.services.iotdata.AWSIotDataClientBuilder;

@Configuration
public class AwsConfig {
    @Bean
    public AWSIot getIotClient(AwsAppConfig awsAppConfig) {
        return AWSIotClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                    (new BasicAWSCredentials(awsAppConfig.getAwsAccessKeyId(), awsAppConfig.getAwsSecretAccessKey()))
                ))
                .withRegion(Regions.AP_SOUTH_1)
                .build();
                
    }

    @Bean
    public AWSIotDataClient getIotDataClient(final AwsAppConfig awsAppConfig) {
        return (AWSIotDataClient) AWSIotDataClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(
                    new AWSCredentials() {
                        public String getAWSSecretKey() {
                            return awsAppConfig.getAwsSecretAccessKey();
                        }

                        public String getAWSAccessKeyId() {
                            return awsAppConfig.getAwsAccessKeyId();
                        }
                }))
                .withRegion(Regions.AP_SOUTH_1)
                .build();

    }
}
