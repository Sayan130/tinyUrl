package org.sdi.tinyurl.configuration;

import java.net.URI;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamodbConfig {

    @Bean
    public DynamoDbClient getDynamodbClient() {

        AwsBasicCredentials credentialsProvider =
            AwsBasicCredentials.builder().accessKeyId("91drvg").secretAccessKey("8bovmq").build();

        return  DynamoDbClient.builder().endpointOverride(URI.create("http://localhost:7070")).credentialsProvider(
                StaticCredentialsProvider.create(credentialsProvider)).build();

    }
}
