package dev.rohitrai.messengerservice.controller;

import dev.rohitrai.messengerservice.model.AddUserInput;
import dev.rohitrai.messengerservice.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"http://localhost:5173", "https://messenger.rohitrai.dev", "https://beta.messenger.rohitrai.dev"})
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

}
