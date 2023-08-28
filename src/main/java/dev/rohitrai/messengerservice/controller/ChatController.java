package dev.rohitrai.messengerservice.controller;

import dev.rohitrai.messengerservice.model.AddMessageInput;
import dev.rohitrai.messengerservice.model.GetMessagesOutput;
import dev.rohitrai.messengerservice.service.ChatService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = {"https://messenger.rohitrai.dev", "https://beta.messenger.rohitrai.dev"})
@RequestMapping("/chat")
@RequiredArgsConstructor
@RestController
public class ChatController {

    @NonNull
    private ChatService chatService;

    @PostMapping("/add-message")
    public ResponseEntity<Void> addMessage(@RequestBody @NonNull AddMessageInput input) {

        return chatService.addMessage(input);
    }

    @GetMapping("/get-messages")
    public ResponseEntity<GetMessagesOutput> getMessages(@RequestParam @NonNull String sender, @RequestParam @NonNull String receiver) {

        return chatService.getMessages(sender, receiver);
    }

}
