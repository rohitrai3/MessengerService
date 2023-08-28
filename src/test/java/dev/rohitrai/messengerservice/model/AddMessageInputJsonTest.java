package dev.rohitrai.messengerservice.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class AddMessageInputJsonTest {

    @Autowired
    private JacksonTester<AddMessageInput> json;

    @Test
    @DisplayName("AddMessageInput serialization test")
    public void addMessageInputSerializationTest() throws IOException {
        AddMessageInput input = AddMessageInput.builder()
                .messageData(MessageData.builder()
                        .sender("johndoe")
                        .receiver("alice")
                        .message("Hello! Alice.")
                        .timestamp(1234567890L)
                        .build())
                .build();

        assertThat(json.write(input)).isStrictlyEqualToJson("expected-add-message-input.json");
        assertThat(json.write(input)).hasJsonPathStringValue("@.messageData.sender");
        assertThat(json.write(input)).extractingJsonPathStringValue("@.messageData.sender").isEqualTo("johndoe");
        assertThat(json.write(input)).hasJsonPathStringValue("@.messageData.receiver");
        assertThat(json.write(input)).extractingJsonPathStringValue("@.messageData.receiver").isEqualTo("alice");
        assertThat(json.write(input)).hasJsonPathNumberValue("@.messageData.timestamp");
        assertThat(json.write(input)).extractingJsonPathNumberValue("@.messageData.timestamp").isEqualTo(1234567890);
    }

    @Test
    @DisplayName("AddMessageInput deserialization test")
    public void addMessageInputDeserializationTest() throws IOException {
        String expected = """
                {
                    "messageData" : {
                        "sender": "johndoe",
                        "receiver": "alice",
                        "message": "Hello! Alice.",
                        "timestamp": 1234567890
                    }
                }
                """;

        assertThat(json.parseObject(expected).getMessageData().getSender()).isEqualTo("johndoe");
        assertThat(json.parseObject(expected).getMessageData().getReceiver()).isEqualTo("alice");
        assertThat(json.parseObject(expected).getMessageData().getMessage()).isEqualTo("Hello! Alice.");
        assertThat(json.parseObject(expected).getMessageData().getTimestamp()).isEqualTo(1234567890L);
    }

}
