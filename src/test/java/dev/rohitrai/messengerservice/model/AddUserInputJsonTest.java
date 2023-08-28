package dev.rohitrai.messengerservice.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class AddUserInputJsonTest {

    @Autowired
    private JacksonTester<AddUserInput> json;

    @Test
    @DisplayName("AddUserInput serialization test")
    public void addUserInputSerializationTest() throws IOException {
        AddUserInput input = AddUserInput.builder()
                .uid("1234abcd")
                .userData(UserData.builder()
                        .username("johndoe")
                        .name("John Doe")
                        .photoUrl("https://example.com/johndoe.png")
                        .build())
                .build();

        assertThat(json.write(input)).isStrictlyEqualToJson("expected-add-user-input.json");
        assertThat(json.write(input)).hasJsonPathStringValue("@.uid");
        assertThat(json.write(input)).extractingJsonPathStringValue("@.uid").isEqualTo("1234abcd");
        assertThat(json.write(input)).hasJsonPathStringValue("@.userData.username");
        assertThat(json.write(input)).extractingJsonPathStringValue("@.userData.username").isEqualTo("johndoe");
        assertThat(json.write(input)).hasJsonPathStringValue("@.userData.name");
        assertThat(json.write(input)).extractingJsonPathStringValue("@.userData.name").isEqualTo("John Doe");
        assertThat(json.write(input)).hasJsonPathStringValue("@.userData.photoUrl");
        assertThat(json.write(input)).extractingJsonPathStringValue("@.userData.photoUrl").isEqualTo("https://example.com/johndoe.png");
    }

    @Test
    @DisplayName("AddUserInput deserialization test")
    public void addUserInputDeserializationTest() throws IOException {
        String expected = """
                {
                    "uid": "1234abcd",
                    "userData": {
                        "username": "johndoe",
                        "name": "John Doe",
                        "photoUrl": "https://example.com/johndoe.png"
                    }
                }
                """;

        assertThat(json.parseObject(expected).getUid()).isEqualTo("1234abcd");
        assertThat(json.parseObject(expected).getUserData().getUsername()).isEqualTo("johndoe");
        assertThat(json.parseObject(expected).getUserData().getName()).isEqualTo("John Doe");
        assertThat(json.parseObject(expected).getUserData().getPhotoUrl()).isEqualTo("https://example.com/johndoe.png");
    }

}
