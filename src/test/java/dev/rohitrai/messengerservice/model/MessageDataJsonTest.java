package dev.rohitrai.messengerservice.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class MessageDataJsonTest {

    @Autowired
    private JacksonTester<MessageData> json;

    @Test
    @DisplayName("MessageData serialization test")
    public void messageDataSerializationTest() throws IOException {
        MessageData messageData = MessageData.builder()
                .sender("johndoe")
                .receiver("alice")
                .message("Hello! Alice.")
                .timestamp(1234567890L)
                .build();

        assertThat(json.write(messageData)).isStrictlyEqualToJson("expected-message-data.json");
        assertThat(json.write(messageData)).hasJsonPathStringValue("@.sender");
        assertThat(json.write(messageData)).extractingJsonPathStringValue("@.sender").isEqualTo("johndoe");assertThat(json.write(messageData)).hasJsonPathStringValue("@.receiver");
        assertThat(json.write(messageData)).extractingJsonPathStringValue("@.receiver").isEqualTo("alice");
        assertThat(json.write(messageData)).hasJsonPathStringValue("@.message");
        assertThat(json.write(messageData)).extractingJsonPathStringValue("@.message").isEqualTo("Hello! Alice.");
        assertThat(json.write(messageData)).hasJsonPathNumberValue("@.timestamp");
        assertThat(json.write(messageData)).extractingJsonPathNumberValue("@.timestamp").isEqualTo(1234567890);
    }

    @Test
    @DisplayName("UserData deserialization test")
    public void userDataDeserializationTest() throws IOException {
        String expected = """
                {
                   "sender": "johndoe",
                   "receiver": "alice",
                   "message": "Hello! Alice.",
                   "timestamp": 1234567890
                }
                """;

        assertThat(json.parseObject(expected).getSender()).isEqualTo("johndoe");
        assertThat(json.parseObject(expected).getReceiver()).isEqualTo("alice");
        assertThat(json.parseObject(expected).getMessage()).isEqualTo("Hello! Alice.");
        assertThat(json.parseObject(expected).getTimestamp()).isEqualTo(1234567890L);
    }

}
