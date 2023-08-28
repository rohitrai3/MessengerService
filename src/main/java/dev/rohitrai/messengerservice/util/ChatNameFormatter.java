package dev.rohitrai.messengerservice.util;

import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ChatNameFormatter {

    public String getChatName(@NonNull String sender, @NonNull String receiver) {

        return sender.compareTo(receiver) < 0 ?
                sender + "_" + receiver :
                receiver + "_" + sender;
    }

}
