package dev.rohitrai.messengerservice.service;

import dev.rohitrai.messengerservice.dao.MessengerDao;
import dev.rohitrai.messengerservice.model.AddMessageInput;
import dev.rohitrai.messengerservice.model.MessageData;
import dev.rohitrai.messengerservice.util.ChatNameFormatter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@Service
public class ChatService {

    @NonNull
    private ChatNameFormatter chatNameFormatter;
    @NonNull
    private MessengerDao messengerDao;

    public ResponseEntity<Void> addMessage(@NonNull AddMessageInput input) {
        MessageData messageData = input.getMessageData();
        String chatName = chatNameFormatter.getChatName(messageData.getSender(), messageData.getReceiver());

        String key = messengerDao.create("chats/" + chatName, messageData);

        URI locationOfNewMessage = UriComponentsBuilder.newInstance()
                .path("chat/{chatName}/{key}")
                .buildAndExpand(chatName, key)
                .toUri();

        return ResponseEntity.created(locationOfNewMessage)
                .build();
    }

}
