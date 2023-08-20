package com.messenger.MessengerService.util.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.messenger.MessengerService.model.MessageData;
import org.springframework.stereotype.Component;

@Component
public class ObjectToMessageDataMapper {

    public MessageData map(Object messageDataObject) {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.convertValue(messageDataObject, MessageData.class);
    }

}
