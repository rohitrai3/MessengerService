package dev.rohitrai.messengerservice.service;

import com.pusher.rest.Pusher;
import dev.rohitrai.messengerservice.dao.MessengerDao;
import dev.rohitrai.messengerservice.model.AddMessageInput;
import dev.rohitrai.messengerservice.model.GetChatNameOutput;
import dev.rohitrai.messengerservice.model.GetMessagesOutput;
import dev.rohitrai.messengerservice.model.MessageData;
import dev.rohitrai.messengerservice.util.ChatNameFormatter;
import dev.rohitrai.messengerservice.util.mapper.ObjectToMessageDataMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatService {

    @NonNull
    private ChatNameFormatter chatNameFormatter;
    @NonNull
    private MessengerDao messengerDao;
    @NonNull
    private ObjectToMessageDataMapper objectToMessageDataMapper;

    public ResponseEntity<Void> addMessage(@NonNull AddMessageInput input) {
        MessageData messageData = input.getMessageData();
        String chatName = chatNameFormatter.getChatName(messageData.getSender(), messageData.getReceiver());

        String key = messengerDao.create("chats/" + chatName, messageData);

        Pusher pusher = new Pusher("", "", "");
        pusher.setCluster("ap2");
        pusher.setEncrypted(true);

        pusher.trigger(chatName, "my-event", input.getMessageData());

        URI locationOfNewMessage = UriComponentsBuilder.newInstance()
                .path("chat/{chatName}/{key}")
                .buildAndExpand(chatName, key)
                .toUri();

        return ResponseEntity.created(locationOfNewMessage)
                .build();
    }

    public ResponseEntity<GetMessagesOutput> getMessages(@NonNull String sender, @RequestParam @NonNull String receiver) {
        String chatName = chatNameFormatter.getChatName(sender, receiver);

        List<Object> messageDataObjectList = messengerDao.readList("chats/" + chatName);

        List<MessageData> messageDataList = messageDataObjectList.stream()
                .map(objectToMessageDataMapper::map)
                .collect(Collectors.toList());

        return ResponseEntity.ok(GetMessagesOutput.builder()
                .messageDataList(messageDataList)
                .build());
    }

    public ResponseEntity<GetChatNameOutput> getChatName(@NonNull String sender, @NonNull String receiver) {
        String chatName = chatNameFormatter.getChatName(sender, receiver);

        return ResponseEntity.ok(GetChatNameOutput.builder()
                .chatName(chatName)
                .build());
    }

}
