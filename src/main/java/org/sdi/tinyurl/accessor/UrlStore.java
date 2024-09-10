package org.sdi.tinyurl.accessor;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.sdi.tinyurl.configuration.DynamodbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

@Component
public class UrlStore {

    public static final String FIELD_NAME = "url";
    public static String TABLE_NAME = "tinyurl2";
    public static String PRIMARY_KEY = "tinyurl";
    @Autowired
    DynamodbConfig dynamodbConfig;

    public void storeUri(String uri, String key) {
        Map<String, AttributeValue> dbKey = new HashMap<>();
        System.out.print("Storing item"+uri+key);
        dbKey.put(PRIMARY_KEY, AttributeValue.builder().s(key).build());
        dbKey.put(FIELD_NAME, AttributeValue.builder().s(uri).build());
        PutItemRequest putItem = PutItemRequest.builder().tableName(TABLE_NAME).item(dbKey).build();
        try {
            dynamodbConfig.getDynamodbClient().putItem(putItem);
            System.out.print("Storing item");
        } catch (DynamoDbException ex) {
            System.out.print("Unexpected Error occurred"+ex);
        }
    }

    public Optional<String> getUri(String key) {

        Map<String, AttributeValue> dbKey = new HashMap<>();
        dbKey.put(PRIMARY_KEY, AttributeValue.builder().s(key).build());

        GetItemRequest request = GetItemRequest.builder().key(dbKey).tableName(TABLE_NAME).build();

        try {
            GetItemResponse response = dynamodbConfig.getDynamodbClient().getItem(request);
            System.out.print("Successfull retrieved"+response);
            return Optional.of(response.item().get(FIELD_NAME).s());
        } catch (DynamoDbException ex) {
            System.out.print("Unexpected Error occurred"+ex);
        }
        return null;
    }
}
