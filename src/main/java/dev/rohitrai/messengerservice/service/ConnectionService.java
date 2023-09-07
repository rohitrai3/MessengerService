package dev.rohitrai.messengerservice.service;

import dev.rohitrai.messengerservice.dao.MessengerDao;
import dev.rohitrai.messengerservice.model.AcceptConnectionRequestInput;
import dev.rohitrai.messengerservice.model.AddConnectionRequestInput;
import dev.rohitrai.messengerservice.model.GetConnectionRequestsOutput;
import dev.rohitrai.messengerservice.model.GetConnectionsOutput;
import dev.rohitrai.messengerservice.model.UserData;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ConnectionService {

    @NonNull
    private MessengerDao messengerDao;
    @NonNull
    private UserService userService;

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
        messengerDao.delete("/requests/" + input.getUser() + "/" + input.getConnectionRequestKey());

        URI locationOfNewConnection = UriComponentsBuilder.newInstance()
                .path("connections/{user}/{key}")
                .buildAndExpand(input.getUser(), key)
                .toUri();

        return ResponseEntity.created(locationOfNewConnection)
                .build();
    }

    public ResponseEntity<GetConnectionRequestsOutput> getConnectionRequests(@NonNull String requestedUsername) {
        Map<String, Object> requestIdToUsername = messengerDao.readMap("requests/" + requestedUsername);

        Map<String, UserData> requestIdToUserData = requestIdToUsername.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e-> userService.getUser(e.getValue().toString()).getBody().getUserData()));

        return ResponseEntity.ok(GetConnectionRequestsOutput.builder()
                .requestIdToUserData(requestIdToUserData)
                .build());
    }

    public ResponseEntity<GetConnectionsOutput> getConnections(@NonNull String requestedUsername) {
        List<UserData> userDataList = new ArrayList<>();

        List<Object> usernameObjectList = messengerDao.readList("connections/" + requestedUsername);

        usernameObjectList.forEach((username) -> {
            UserData userData = userService.getUser(username.toString()).getBody().getUserData();
            userDataList.add(userData);
        });

        return ResponseEntity.ok(GetConnectionsOutput.builder()
                .userDataList(userDataList)
                .build());
    }

}
