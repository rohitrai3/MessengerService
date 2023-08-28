package dev.rohitrai.messengerservice.service;

import dev.rohitrai.messengerservice.dao.MessengerDao;
import dev.rohitrai.messengerservice.model.AcceptConnectionRequestInput;
import dev.rohitrai.messengerservice.model.AddConnectionRequestInput;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@Service
public class ConnectionService {

    @NonNull
    private MessengerDao messengerDao;

    public ResponseEntity<Void> addConnectionRequest(@NonNull AddConnectionRequestInput input) {
        String key = messengerDao.create("requests/" + input.getReceiver(), input.getSender());

        URI locationOfNewConnectionRequest = UriComponentsBuilder.newInstance()
                .path("requests/{receiver}/{key}")
                .buildAndExpand(input.getReceiver(), key)
                .toUri();

        return ResponseEntity.created(locationOfNewConnectionRequest)
                .build();
    }

    public ResponseEntity<Void> acceptConnectionRequest(@NonNull AcceptConnectionRequestInput input) {
        String key = messengerDao.create("connections/" + input.getUser(), input.getConnection());
        messengerDao.create("connections/" + input.getConnection(), input.getUser());
        messengerDao.delete("/requests/" + input.getConnection() + "/" + input.getConnectionRequestKey());

        URI locationOfNewConnection = UriComponentsBuilder.newInstance()
                .path("connections/{user}/{key}")
                .buildAndExpand(input.getUser(), key)
                .toUri();

        return ResponseEntity.created(locationOfNewConnection)
                .build();
    }

}
