package dev.rohitrai.messengerservice.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class GetChatNameOutputJsonTest {

    @Autowired
    private JacksonTester<GetChatNameOutput> json;

    @DisplayName("GetChatNameOutput serialization test")
    @Test
    public void getChatNameSerializationTest() throws IOException {
        GetChatNameOutput output = GetChatNameOutput.builder()
                .chatName("alice_johndoe")
                .build();

        assertThat(json.write(output)).isStrictlyEqualToJson("expected-get-chat-name-output.json");
        assertThat(json.write(output)).hasJsonPathStringValue("@.chatName");
        assertThat(json.write(output)).extractingJsonPathStringValue(("@.chatName")).isEqualTo("alice_johndoe");
    }
    @DisplayName("GetChatNameOutput deserialization test")
    @Test
    public void getChatNameDeserializationTest() throws IOException {
        String expected = """
                {
                	"chatName": "alice_johndoe"
                }
                """;

        assertThat(json.parseObject(expected).getChatName()).isEqualTo("alice_johndoe");
    }

}
