package com.messenger.MessengerService.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class GetMessagesOutputJsonTest {

    @Autowired
    private JacksonTester<GetMessagesOutput> json;

    @Test
    @DisplayName("GetMessagesOutput serialization test")
    public void getMessagesOutputSerializationTest() throws IOException {
        List<MessageData> messageDataList = new ArrayList<>();
        messageDataList.add(MessageData.builder()
                .sender("johndoe")
                .receiver("alice")
                .message("Hello! Alice.")
                .timestamp(1234567890L)
                .build());
        messageDataList.add(MessageData.builder()
                .sender("alice")
                .receiver("johndoe")
                .message("Hi! John. How are you?")
                .timestamp(1234567891L)
                .build());
        GetMessagesOutput output = GetMessagesOutput.builder()
                .messageDataList(messageDataList)
                .build();

        assertThat(json.write(output)).isStrictlyEqualToJson("expected-get-messages-output.json");
        assertThat(json.write(output)).hasJsonPathArrayValue("@.messageDataList");
        assertThat(json.write(output)).extractingJsonPathArrayValue("@.messageDataList").size().isEqualTo(2);
        assertThat(json.write(output)).extractingJsonPathArrayValue("@.messageDataList").element(0).extracting("sender").isEqualTo("johndoe");
        assertThat(json.write(output)).extractingJsonPathArrayValue("@.messageDataList").element(0).extracting("receiver").isEqualTo("alice");
        assertThat(json.write(output)).extractingJsonPathArrayValue("@.messageDataList").element(0).extracting("message").isEqualTo("Hello! Alice.");
        assertThat(json.write(output)).extractingJsonPathArrayValue("@.messageDataList").element(0).extracting("timestamp").isEqualTo(1234567890);
        assertThat(json.write(output)).extractingJsonPathArrayValue("@.messageDataList").element(1).extracting("sender").isEqualTo("alice");
        assertThat(json.write(output)).extractingJsonPathArrayValue("@.messageDataList").element(1).extracting("receiver").isEqualTo("johndoe");
        assertThat(json.write(output)).extractingJsonPathArrayValue("@.messageDataList").element(1).extracting("message").isEqualTo("Hi! John. How are you?");
        assertThat(json.write(output)).extractingJsonPathArrayValue("@.messageDataList").element(1).extracting("timestamp").isEqualTo(1234567891);
    }

    @Test
    @DisplayName("GetMessagesOutput deserialization test")
    public void getMessagesOutputDeserializationTest() throws IOException {
        String expected = """
                {
                    "messageDataList": [
                        {
                            "sender": "johndoe",
                            "receiver": "alice",
                            "message": "Hello! Alice.",
                            "timestamp": 1234567890
                        },
                        {
                            "sender": "alice",
                            "receiver": "johndoe",
                            "message": "Hi! John. How are you?",
                            "timestamp": 1234567891
                        }
                    ]
                }
                """;

        assertThat(json.parseObject(expected).getMessageDataList()).size().isEqualTo(2);
        assertThat(json.parseObject(expected).getMessageDataList()).element(0).extracting("sender").isEqualTo("johndoe");
        assertThat(json.parseObject(expected).getMessageDataList()).element(0).extracting("receiver").isEqualTo("alice");
        assertThat(json.parseObject(expected).getMessageDataList()).element(0).extracting("message").isEqualTo("Hello! Alice.");
        assertThat(json.parseObject(expected).getMessageDataList()).element(0).extracting("timestamp").isEqualTo(1234567890L);
        assertThat(json.parseObject(expected).getMessageDataList()).element(1).extracting("sender").isEqualTo("alice");
        assertThat(json.parseObject(expected).getMessageDataList()).element(1).extracting("receiver").isEqualTo("johndoe");
        assertThat(json.parseObject(expected).getMessageDataList()).element(1).extracting("message").isEqualTo("Hi! John. How are you?");
        assertThat(json.parseObject(expected).getMessageDataList()).element(1).extracting("timestamp").isEqualTo(1234567891L);
    }

}
