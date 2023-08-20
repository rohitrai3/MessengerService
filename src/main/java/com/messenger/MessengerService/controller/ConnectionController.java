package com.messenger.MessengerService.controller;

import com.messenger.MessengerService.model.AcceptConnectionRequestInput;
import com.messenger.MessengerService.model.AddConnectionRequestInput;
import com.messenger.MessengerService.model.GetConnectionRequestsOutput;
import com.messenger.MessengerService.model.GetConnectionsOutput;
import com.messenger.MessengerService.service.ConnectionService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:5173")
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

    @PostMapping("/accept-connection-request")
    private ResponseEntity<Void> acceptConnectionRequest(@RequestBody @NonNull AcceptConnectionRequestInput input) {

        return connectionService.acceptConnectionRequest(input);
    }

    @GetMapping("/get-connection-requests/{requestedUsername}")
    public ResponseEntity<GetConnectionRequestsOutput> getConnectionRequests(@PathVariable @NonNull String requestedUsername) {

        return connectionService.getConnectionRequests(requestedUsername);
    }

    @GetMapping("/get-connections/{requestedUsername}")
    public ResponseEntity<GetConnectionsOutput> getConnections(@PathVariable @NonNull String requestedUsername) {

        return connectionService.getConnections(requestedUsername);
    }

}
