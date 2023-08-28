package dev.rohitrai.messengerservice.util.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rohitrai.messengerservice.model.UserData;
import org.springframework.stereotype.Component;

@Component
public class ObjectToUserDataMapper {

    public UserData map(Object userDataObject) {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.convertValue(userDataObject, UserData.class);
    }

}
