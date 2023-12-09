package com.example.photographer.service.push;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Slf4j
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class FirebasePushClient implements PushClient {

    public FirebasePushClient(String path) {
        init(path);
    }

    private void init(String path) {
        Path p = Paths.get(path);

        try (InputStream serviceAccount = Files.newInputStream(p)) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            log.warn("FirebaseClient initialise exception", e);
        }
    }

    @Override
    public boolean send(String topic, String message) {
        return false;
    }
}
