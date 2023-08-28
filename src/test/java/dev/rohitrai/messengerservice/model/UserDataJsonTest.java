package dev.rohitrai.messengerservice.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class UserDataJsonTest {

    @Autowired
    private JacksonTester<UserData> json;

    @Test
    @DisplayName("UserData serialization test")
    public void userDataSerializationTest() throws IOException {
        UserData userData = UserData.builder()
                .username("johndoe")
                .name("John Doe")
                .photoUrl("https://example.com/johndoe.png")
                .build();

        assertThat(json.write(userData)).isStrictlyEqualToJson("expected-user-data.json");
        assertThat(json.write(userData)).hasJsonPathStringValue("@.username");
        assertThat(json.write(userData)).extractingJsonPathStringValue("@.username").isEqualTo("johndoe");
        assertThat(json.write(userData)).hasJsonPathStringValue("@.name");
        assertThat(json.write(userData)).extractingJsonPathStringValue("@.name").isEqualTo("John Doe");
        assertThat(json.write(userData)).hasJsonPathStringValue("@.photoUrl");
        assertThat(json.write(userData)).extractingJsonPathStringValue("@.photoUrl").isEqualTo("https://example.com/johndoe.png");
    }

    @Test
    @DisplayName("UserData deserialization test")
    public void userDataDeserializationTest() throws IOException {
        String expected = """
                {
                   "username": "johndoe",
                   "name": "John Doe",
                   "photoUrl": "https://example.com/johndoe.png"
                }
                """;

        assertThat(json.parseObject(expected).getUsername()).isEqualTo("johndoe");
        assertThat(json.parseObject(expected).getName()).isEqualTo("John Doe");
        assertThat(json.parseObject(expected).getPhotoUrl()).isEqualTo("https://example.com/johndoe.png");
    }

}
