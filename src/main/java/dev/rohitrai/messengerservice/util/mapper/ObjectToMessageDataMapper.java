package dev.rohitrai.messengerservice.util.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rohitrai.messengerservice.model.MessageData;
import org.springframework.stereotype.Component;

@Component
public class ObjectToMessageDataMapper {

    public MessageData map(Object messageDataObject) {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.convertValue(messageDataObject, MessageData.class);
    }

}
