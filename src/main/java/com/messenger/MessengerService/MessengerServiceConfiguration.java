package com.messenger.MessengerService;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
@Log4j2
public class MessengerServiceConfiguration {

    @Bean
    public void initializeFirebase() throws IOException {
        log.info("Initializing Firebase...");
        Resource serviceAccountKey = new ClassPathResource("static/service-account-key.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountKey.getInputStream()))
                .setDatabaseUrl("https://messenger-f035e-default-rtdb.asia-southeast1.firebasedatabase.app")
                .build();

        FirebaseApp.initializeApp(options);

        log.info("...Firebase initialized.");
    }
}
