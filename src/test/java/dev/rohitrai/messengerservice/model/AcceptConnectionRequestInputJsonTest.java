package dev.rohitrai.messengerservice.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class AcceptConnectionRequestInputJsonTest {

    @Autowired
    private JacksonTester<AcceptConnectionRequestInput> json;

    @Test
    @DisplayName("AcceptConnectionRequestInput serialization test")
    public void acceptConnectionRequestInputSerializationTest() throws IOException {
        AcceptConnectionRequestInput input = AcceptConnectionRequestInput.builder()
                .user("johndoe")
                .connection("alice")
                .connectionRequestKey("12345")
                .build();

        assertThat(json.write(input)).isStrictlyEqualToJson("expected-accept-connection-request-input.json");
        assertThat(json.write(input)).hasJsonPathStringValue("@.user");
        assertThat(json.write(input)).extractingJsonPathStringValue("@.user").isEqualTo("johndoe");
        assertThat(json.write(input)).hasJsonPathStringValue("@.connection");
        assertThat(json.write(input)).extractingJsonPathStringValue("@.connection").isEqualTo("alice");
        assertThat(json.write(input)).hasJsonPathStringValue("@.connectionRequestKey");
        assertThat(json.write(input)).extractingJsonPathStringValue("@.connectionRequestKey").isEqualTo("12345");
    }

    @Test
    @DisplayName("AcceptConnectionRequestInput deserialization test")
    public void acceptConnectionRequestInputDeserializationTest() throws IOException {
        String expected = """
                {
                    "user": "johndoe",
                    "connection": "alice",
                    "connectionRequestKey": "12345"
                }
                """;

        assertThat(json.parseObject(expected).getUser()).isEqualTo("johndoe");
        assertThat(json.parseObject(expected).getConnection()).isEqualTo("alice");
        assertThat(json.parseObject(expected).getConnectionRequestKey()).isEqualTo("12345");
    }

}
