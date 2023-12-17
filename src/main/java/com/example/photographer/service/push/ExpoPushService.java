package com.example.photographer.service.push;


import io.github.jav.exposerversdk.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExpoPushService implements PushService {

    PushClient pushClient;

    @Override
    public void send(MessageContext messageContext) {
        send(messageContext.getTokens(), messageContext.getTitle(), messageContext.getMessage(), messageContext.getData());
    }

    @Override
    public void send(List<String> tokens, String title, String message, Map<String, Object> data) {
        List<String> validTokens = filterTokens(tokens);
        if (validTokens.isEmpty()) {
            return;
        }

        ExpoPushMessage expoPushMessage = new ExpoPushMessage();
        expoPushMessage.getTo().addAll(tokens);
        expoPushMessage.setTitle(title);
        expoPushMessage.setBody(message);
        expoPushMessage.setData(data);

        List<ExpoPushMessage> expoPushMessages = new ArrayList<>();
        expoPushMessages.add(expoPushMessage);

        List<List<ExpoPushMessage>> chunks = pushClient.chunkPushNotifications(expoPushMessages);

        List<CompletableFuture<List<ExpoPushTicket>>> messageRepliesFutures = new ArrayList<>();

        for (List<ExpoPushMessage> chunk : chunks) {
            messageRepliesFutures.add(pushClient.sendPushNotificationsAsync(chunk));
        }

        // Wait for each completable future to finish
        List<ExpoPushTicket> allTickets = new ArrayList<>();
        for (CompletableFuture<List<ExpoPushTicket>> messageReplyFuture : messageRepliesFutures) {
            try {
                allTickets.addAll(messageReplyFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        List<ExpoPushMessageTicketPair<ExpoPushMessage>> zippedMessagesTickets = pushClient.zipMessagesTickets(expoPushMessages, allTickets);

        List<ExpoPushMessageTicketPair<ExpoPushMessage>> okTicketMessages = pushClient.filterAllSuccessfulMessages(zippedMessagesTickets);
        if (!okTicketMessages.isEmpty()) {
            String okTicketMessagesString = okTicketMessages.stream().map(p -> "Title: " + p.message.getTitle() + ", Id:" + p.ticket.getId()).collect(Collectors.joining(","));
            log.info("Recieved OK ticket for " + okTicketMessages.size() + " messages: " + okTicketMessagesString);
        }

        List<ExpoPushMessageTicketPair<ExpoPushMessage>> errorTicketMessages = pushClient.filterAllMessagesWithError(zippedMessagesTickets);
        if (!errorTicketMessages.isEmpty()) {
            String errorTicketMessagesString = errorTicketMessages.stream().map(p -> "Title: " + p.message.getTitle() + ", Error: " + p.ticket.getDetails().getError()).collect(Collectors.joining(","));
            log.warn("Recieved ERROR ticket for " + errorTicketMessages.size() + " messages: " + errorTicketMessagesString);
        }

    }

    @Override
    public boolean validateToken(String token) {
        return PushClient.isExponentPushToken(token);
    }

    private List<String> filterTokens(List<String> tokens) {
        List<String> validated = new ArrayList<>();

        for (String token : tokens) {
            if (!validateToken(token)) {
                log.warn("Token:" + tokens + " is not a valid token.");
                validated.add(token);
            }
        }

        return validated;
    }
}
