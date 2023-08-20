package com.messenger.MessengerService;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class MessengerServiceConfiguration {

    @Bean
    public void initializeFirebase() throws IOException {
        System.out.println("Initializing Firebase...");
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .setDatabaseUrl("https://messenger-f035e-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .build();
        FirebaseApp.initializeApp(options);
        System.out.println("...Firebase initialized.");
    }
}
