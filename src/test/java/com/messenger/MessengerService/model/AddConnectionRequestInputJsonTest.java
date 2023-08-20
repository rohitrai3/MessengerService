package com.messenger.MessengerService.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class AddConnectionRequestInputJsonTest {

    @Autowired
    private JacksonTester<AddConnectionRequestInput> json;

    @Test
    @DisplayName("AddConnectionRequestInput serialization test")
    public void addConnectionRequestInputRequestSerializationTest() throws IOException {
        AddConnectionRequestInput input = AddConnectionRequestInput.builder()
                .sender("johndoe")
                .receiver("alice")
                .build();

        assertThat(json.write(input)).isStrictlyEqualToJson("expected-add-connection-request-input.json");
        assertThat(json.write(input)).hasJsonPathStringValue("@.sender");
        assertThat(json.write(input)).extractingJsonPathStringValue("@.sender").isEqualTo("johndoe");
        assertThat(json.write(input)).hasJsonPathStringValue("@.receiver");
        assertThat(json.write(input)).extractingJsonPathStringValue("@.receiver").isEqualTo("alice");
    }

    @Test
    @DisplayName("AddConnectionRequestInput deserialization test")
    public void addConnectionRequestInputRequestDeserializationTest() throws IOException {
        String expected = """
                {
                    "sender": "johndoe",
                    "receiver": "alice"
                }
                """;

        assertThat(json.parseObject(expected).getSender()).isEqualTo("johndoe");
        assertThat(json.parseObject(expected).getReceiver()).isEqualTo("alice");
    }

}
