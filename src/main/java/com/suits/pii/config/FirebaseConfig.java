package com.suits.pii.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.credentials}")
    private String credentials;

    @Value("${firebase.storage}")
    private String storage;

    @Bean
    public Bucket getBucket() throws IOException {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(getCredentials())
                .setStorageBucket(storage)
                .build();
        FirebaseApp.initializeApp(options);
        return StorageClient.getInstance().bucket();
    }

    private GoogleCredentials getCredentials() throws IOException {
        return GoogleCredentials.fromStream(new ByteArrayInputStream(credentials.getBytes(StandardCharsets.UTF_8)));
    }
}
