package com.messenger.MessengerService.controller;

import com.messenger.MessengerService.model.AddUserInput;
import com.messenger.MessengerService.model.CheckUidExistOutput;
import com.messenger.MessengerService.model.CheckUsernameExistOutput;
import com.messenger.MessengerService.model.GetUserOutput;
import com.messenger.MessengerService.model.GetUsernameOutput;
import com.messenger.MessengerService.service.UserService;
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

@CrossOrigin(origins = {"https://messenger.rohitrai.dev", "https://beta.messenger.rohitrai.dev"})
@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    @NonNull
    private UserService userService;

    @PostMapping("/add-user")
    public ResponseEntity<Void> addUser(@RequestBody @NonNull AddUserInput input) {

        return userService.addUser(input);
    }

    @GetMapping("/get-username/{requestedUid}")
    public ResponseEntity<GetUsernameOutput> getUsername(@PathVariable @NonNull String requestedUid) {

        return userService.getUsername(requestedUid);
    }

    @GetMapping("/get-user/{requestedUsername}")
    public ResponseEntity<GetUserOutput> getUser(@PathVariable @NonNull String requestedUsername) {

        return userService.getUser(requestedUsername);
    }

    @GetMapping("/check-uid-exist/{requestedUid}")
    public ResponseEntity<CheckUidExistOutput> checkUidExist(@PathVariable @NonNull String requestedUid) {

        return userService.checkUidExist(requestedUid);
    }

    @GetMapping("/check-username-exist/{requestedUsername}")
    public ResponseEntity<CheckUsernameExistOutput> checkUsernameExist(@PathVariable @NonNull String requestedUsername) {

        return userService.checkUsernameExist(requestedUsername);
    }

}
