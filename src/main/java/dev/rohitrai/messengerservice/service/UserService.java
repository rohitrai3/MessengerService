package dev.rohitrai.messengerservice.service;

import dev.rohitrai.messengerservice.dao.MessengerDao;
import dev.rohitrai.messengerservice.model.AddUserInput;
import dev.rohitrai.messengerservice.model.GetUserOutput;
import dev.rohitrai.messengerservice.model.GetUsernameOutput;
import dev.rohitrai.messengerservice.model.UserData;
import dev.rohitrai.messengerservice.util.mapper.ObjectToUserDataMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@Service
public class UserService {

    @NonNull
    private MessengerDao messengerDao;
    @NonNull
    private ObjectToUserDataMapper objectToUserDataMapper;

    public ResponseEntity<Void> addUser(@NonNull AddUserInput input) {
        UserData inputUserData = input.getUserData();

        messengerDao.create("users/username", input.getUid(), inputUserData.getUsername());
        messengerDao.create("users/info", inputUserData.getUsername(), inputUserData);

        URI locationOfNewUserData = UriComponentsBuilder.newInstance()
                .path("users/info/{username}")
                .buildAndExpand(inputUserData.getUsername())
                .toUri();

        return ResponseEntity.created(locationOfNewUserData)
                .build();
    }

    public ResponseEntity<GetUsernameOutput> getUsername(@NonNull String requestedUid) {
        Object usernameObject = messengerDao.read("users/username/" + requestedUid);

        if (usernameObject == null) {

            return ResponseEntity.notFound()
                    .build();
        }

        return ResponseEntity.ok(GetUsernameOutput.builder()
                .username(usernameObject.toString())
                .build());
    }

    public ResponseEntity<GetUserOutput> getUser(@NonNull String requestedUsername) {
        Object userDataObject = messengerDao.read("users/info/" + requestedUsername);

        if (userDataObject == null) {

            return ResponseEntity.notFound()
                    .build();
        }

        return ResponseEntity.ok(GetUserOutput.builder()
                .userData(objectToUserDataMapper.map(userDataObject))
                .build());
    }

}
