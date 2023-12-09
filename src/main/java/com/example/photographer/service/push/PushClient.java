package com.example.photographer.service.push;

public interface PushClient {

    boolean send(String topic, String message);
}
