package com.example.photographer.service.push;


import java.util.List;
import java.util.Map;

public interface PushService {

    void send(MessageContext messageContext);

    void send(List<String> tokens, String title, String message, Map<String, Object> data);

    boolean validateToken(String token);

}
