package dev.rohitrai.messengerservice.controller;

import dev.rohitrai.messengerservice.model.AddConnectionRequestInput;
import dev.rohitrai.messengerservice.service.ConnectionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:5173", "https://messenger.rohitrai.dev", "https://beta.messenger.rohitrai.dev"})
@RequestMapping("/connection")
@RequiredArgsConstructor
@RestController
public class ConnectionController {

    @NonNull
    private ConnectionService connectionService;

    @PostMapping("/add-connection-request")
    private ResponseEntity<Void> addConnectionRequest(@RequestBody @NonNull AddConnectionRequestInput input) {

        return connectionService.addConnectionRequest(input);
    }

}
